package br.com.loglogistica.logapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ListaActivity extends ListActivity {

    // ==============================================================================================================
    // DECLARAÇÕES DIVERSAS
    // ==============================================================================================================
    public  ListView lv;
    ProgressDialog progressDialog;

    //Volley conectividade. ATENÇÃO ID do Motoboy
    public static final String JSON_URL = "http://webservice21214.azurewebsites.net/wservice.asmx/ListaEntregas?IdMotoboy=1";
    private static final String TAG = "ListaActivity";

    // ==============================================================================================================
    // CICLO DA ACTIVITY
    // ==============================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lv = (ListView) findViewById(android.R.id.list);
        progressDialog = new ProgressDialog(this);
    }

    public void btatualizar(View view){
        volleyStringRequst(JSON_URL);
    }


    //======================================================================================================================
    //VOLLEY CONECTIVIDADE - TROCA DE DADOS COM WEB-SERVICE
    //=====================================================================================================================
    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();

        ListaAdapter cl = new ListaAdapter(this, ParseJSON.Bairros, ParseJSON.Enderecos);
        lv.setAdapter(cl);
    }


    //======================================================================================================================
    //VOLLEY CONECTIVIDADE - TROCA DE DADOS COM WEB-SERVICE
    //======================================================================================================================
    public void volleyStringRequst(String url){

        String  REQUEST_TAG = "br.com.loglogistica.volleyStringRequest";
        progressDialog.setMessage("Aguarde...");
        progressDialog.show();

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                int tamanho=response.toString().length();
                String str1 = "return : {" + response.toString().substring(24,tamanho-9) + "}";
                showJSON(str1);
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

}
