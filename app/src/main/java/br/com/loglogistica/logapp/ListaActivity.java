package br.com.loglogistica.logapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

        //requisita lista de entregas
        volleyStringRequst(JSON_URL);
    }
    
    //======================================================================================================================
    //VOLLEY CONECTIVIDADE - TROCA DE DADOS COM WEB-SERVICE
    //=====================================================================================================================
    private void showJSON(String json){
        //monta Array String com lista de Entregas
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
                //Formata retorno obtido do web-service (padrão parsing JSON)
                String str1 =  "{\"entregas\":" + response.toString().substring(63);
                int tamanho=str1.length() -9 ;
                String str2 = str1.substring(0,tamanho) + "}";

                //envia retorno para processo de Parsing
                showJSON(str2);
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
