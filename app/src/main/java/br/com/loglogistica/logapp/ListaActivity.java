package br.com.loglogistica.logapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

public class ListaActivity extends ListActivity {

    // ==============================================================================================================
    // DECLARAÇÕES DIVERSAS
    // ==============================================================================================================
    public  ListView lv;
    ProgressDialog progressDialog;

    //Volley conectividade. ATENÇÃO para ID do Motoboy. Buscar em Configurações
    public static final String JSON_URL = "http://webservice21214.azurewebsites.net/wservice.asmx/ListaEntregas?IdMotoboy=1";
    private static final String TAG = "ListaActivity";

    // array inicial - para efeito de testes, corrigir para buscar somente pelo volley
    String[] Linguagens = {
            "PITUBA",
            "ACM",
            "BARRA",
            "PITUBA",
            "ACM",
            "BARRA",
            "BARRA"};

    String[] Descricao= {
            "Rua Pedro Moraes, 32",
            "Trav. Pedro Luiz, 987",
            "Rua das Bromélias, 22",
            "Quadra 22, Lote 67, Gleba A",
            "Rua Mesquita, 50",
            "Av. Santana de Assis, 321",
            "Rua Pedro Moraes, 234, Prox. Mercado"
    };

    // ==============================================================================================================
    // CICLO DA ACTIVITY
    // ==============================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lv = (ListView) findViewById(android.R.id.list);
        progressDialog = new ProgressDialog(this);

        //preenche lista com dados de teste - apagar
        ListaAdapter lista = new ListaAdapter(this,Linguagens ,Descricao);
        setListAdapter(lista);
    }

    public void btatualizar(View view){
        volleyStringRequst(JSON_URL);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();

        //ListaAdapter cl = new ListaAdapter(this, ParseJSON.Bairros, ParseJSON.Enderecos);
        //lv.setAdapter(cl);
    }


    //======================================================================================================================
    //VOLLEY CONECTIVIDADE - TROCA DE DADOS COM WEB-SERVICE
    //======================================================================================================================
    public void volleyStringRequst(String url){

        progressDialog.setMessage("Atualizando...");
        progressDialog.show();

        String  REQUEST_TAG = "br.com.loglogistica.volleyStringRequst";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
                progressDialog.hide();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Erro Envio: " + error.getMessage());
                Toast.makeText(ListaActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                progressDialog.hide();
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
