package br.com.loglogistica.logapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // ==============================================================================================================
    // DECLARAÇÕES DIVERSAS

    DateFormat dateFormat,horaFormat;
    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    String lat, lon;

    String IdMotoboy="0",IdEntrega="0";
    Button btLista,btMapa;
    TextView txtID;

    //Volley conectividade
    private static String STRING_REQUEST_URL="";
    private static final String TAG = "MainActivity";

    // ==============================================================================================================


    // ==============================================================================================================
    // CICLO DA ACTIVITY
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btLista = (Button) findViewById(R.id.btLista);
        btMapa = (Button) findViewById(R.id.btMapa);
        txtID = (TextView) findViewById(R.id.txtID);

        //Google API
        buildGoogleApiClient();

        // Identifica ID do Motoboy
        IdentificaID();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    public void IdentificaID(){

        SharedPreferences preferences = getSharedPreferences("LOG_CONFIG",Context.MODE_PRIVATE);
        if (preferences.contains(("IDMotoboy"))){
            // Salva ID em variável Global para ser utilizado nas outras Activitys
            Global.globalID = preferences.getString("IDMotoboy","0");
            IdMotoboy = Global.globalID;
            txtID.setText("ID: " + Global.globalID);
        }else{
            btLista.setEnabled(false);
            btMapa.setEnabled(false);
        }

    }

    // ==============================================================================================================


    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void btTransito(View view){
        Intent it = new Intent(this, MapsActivity.class);
        startActivity(it);
    }

    public void btLista(View view){
        Intent it = new Intent(this, ListaActivity.class);
        startActivity(it);
    }


    //======================================================================================================================
    //GEOLOCALIZAÇÃO
    //======================================================================================================================
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000); // Atualizaçao a cada : 10 segundos

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());
        }

        // envia dados de localização utilizando Volley Library
        // ==============================================================================================================
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        horaFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        STRING_REQUEST_URL="http://webservice21214.azurewebsites.net/wservice.asmx/Historico?IDMotoboy="+ IdMotoboy + "&identrega="
                + IdEntrega + "&latitude=" + lat + "&longitude=" + lon + "&dataleitura=" + dateFormat.format(date) + "%20" + horaFormat.format(date);
        volleyStringRequst(STRING_REQUEST_URL);
        // ==============================================================================================================

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    @Override
    public void onLocationChanged(Location location) {

        lat = String.valueOf(location.getLatitude());
        lon = String.valueOf(location.getLongitude());

        // envia dados de localização utilizando Volley library
        // ==============================================================================================================
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        horaFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        STRING_REQUEST_URL="http://webservice21214.azurewebsites.net/wservice.asmx/Historico?IDMotoboy="+ IdMotoboy + "&identrega="
                + IdEntrega + "&latitude=" + lat + "&longitude=" + lon + "&dataleitura=" + dateFormat.format(date) + "%20" + horaFormat.format(date);
        volleyStringRequst(STRING_REQUEST_URL);
        // ==============================================================================================================

    }


    //======================================================================================================================
    //MENU
    //======================================================================================================================
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        MenuItem op1 = menu.add(0,0,0,"Configurações");
        op1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int panel, MenuItem item){
        switch (item.getItemId()){
            case 0 :
                Intent it = new Intent(this, ConfigActivity.class);
                startActivity(it);
                break;
        }
        return true;
    }


    //======================================================================================================================
    //VOLLEY CONECTIVIDADE - TROCA DE DADOS COM WEB-SERVICE
    public void volleyStringRequst(String url){

        String  REQUEST_TAG = "br.com.loglogistica.volleyStringRequst";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Falha no Envio de Dados", Toast.LENGTH_LONG).show();
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void volleyInvalidateCache(String url){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().invalidate(url, true);
    }

    public void volleyDeleteCache(String url){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().remove(url);
    }

    public void volleyClearCache(){
        AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
    }

}