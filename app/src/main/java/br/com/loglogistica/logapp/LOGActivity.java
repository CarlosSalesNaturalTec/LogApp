package br.com.loglogistica.logapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class LOGActivity extends Activity {

    TextView txtLOG;
    Button btApagarLOG;
    String IdMotoboy="0";
    String textoLog;
    DateFormat dateFormat,horaFormat;

    //Volley conectividade
    private static String STRING_REQUEST_URL="";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        txtLOG = (TextView) findViewById(R.id.txtLOG);
        btApagarLOG = (Button) findViewById(R.id.btApagarLOG);

        IdentificaID();

        try {
            FileInputStream fin = openFileInput("LOG_Registro");
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            textoLog = temp;
            txtLOG.setText(textoLog);
        }
        catch(Exception e){
        }
    }


    public void EnviarLOG(View view){

        STRING_REQUEST_URL="http://logwsdiv.azurewebsites.net/webservice.asmx/recuperalog?param1=" + IdMotoboy + "&param2=" + textoLog;
        volleyStringRequst(STRING_REQUEST_URL);

    }

    public void IdentificaID(){
        SharedPreferences preferences = getSharedPreferences("LOG_CONFIG", Context.MODE_PRIVATE);
        if (preferences.contains(("IDMotoboy"))){
            IdMotoboy = preferences.getString("IDMotoboy","0");
        }else{
            Toast.makeText(LOGActivity.this, "Atenção! Configure ID do Motoboy", Toast.LENGTH_LONG).show();
        }
    }



    //======================================================================================================================
    //VOLLEY CONECTIVIDADE - TROCA DE DADOS COM WEB-SERVICE
    public void volleyStringRequst(String url){

        String  REQUEST_TAG = "br.com.loglogistica.logAtividades";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                horaFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();

                try {
                    String string = horaFormat.format(date) + "hs_" + "LOGEnviado" + "\n";
                    FileOutputStream fos = openFileOutput("LOG_Registro", Context.MODE_PRIVATE);
                    fos.write(string.getBytes());
                    fos.close();

                    Toast.makeText(LOGActivity.this, "LOG Enviado com Sucesso", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Não foi possivel gravar arquivo de LOG", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LOGActivity.this, "Não foi possível Enviar LOG", Toast.LENGTH_LONG).show();
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


    public void ApagarLOG(View view){

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        horaFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        try {
            String string = horaFormat.format(date) + "hs_" + "LOGApagado" + "\n";
            FileOutputStream fos = openFileOutput("LOG_Registro", Context.MODE_PRIVATE);
            fos.write(string.getBytes());
            fos.close();

            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Não foi possivel gravar arquivo de LOG", Toast.LENGTH_SHORT).show();
        }

    }
}
