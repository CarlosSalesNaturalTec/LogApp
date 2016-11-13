package br.com.loglogistica.logapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class DetalhesActivity extends Activity  {

    Button botaoConcluir, botaoMapa, botaoStartTravel;
    TextView txtNome, txtBairro, txtEnderec, txtPref, txtCidade, txtTelefone, txtObs;
    ProgressDialog progressDialog;
    public static String JSON_URL = "", MapLat, MapLongt;


    //======================================================================================================================
    //Ciclo da Activity - on Create
    //======================================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        //inicializa componentes
        botaoConcluir = (Button) findViewById(R.id.btConcluida);
        botaoMapa = (Button) findViewById(R.id.btMap);
        botaoStartTravel = (Button) findViewById(R.id.btStart);

        txtNome = (TextView) findViewById(R.id.txtNome);
        txtEnderec = (TextView) findViewById(R.id.txtEnderec);
        txtBairro = (TextView) findViewById(R.id.txtBairro);
        txtCidade = (TextView) findViewById(R.id.txtCidade);
        txtPref= (TextView) findViewById(R.id.txtPref);
        txtCidade = (TextView) findViewById(R.id.txtCidade);
        txtTelefone = (TextView) findViewById(R.id.txtTelefone);
        txtObs = (TextView) findViewById(R.id.txtObs);

        //monta Spinner (combo com lista de opções)
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                R.array.status_array, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //aguarda seleção do usuario no Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    botaoConcluir.setEnabled(false);
                    botaoStartTravel.setEnabled(true);
                    botaoMapa.setEnabled(true);
                }else{
                    botaoConcluir.setEnabled(true);
                    botaoStartTravel.setEnabled(false);
                    botaoMapa.setEnabled(false);
                }

                String itemSelecao = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //recupera dados passados da Activity anterior
        Bundle b = getIntent().getExtras();
        String IdEntrega = b.getString("IDEntrega");

        //requisita detalhes de entrega
        JSON_URL = "http://webservice21214.azurewebsites.net/wservice.asmx/DetalhesEntrega?IdEntrega=" + IdEntrega;
        volleyStringRequst(JSON_URL);

    }

    //======================================================================================================================
    //VOLLEY CONECTIVIDADE - TROCA DE DADOS COM WEB-SERVICE
    //======================================================================================================================
    public void volleyStringRequst(String url){

        String  REQUEST_TAG = "br.com.loglogistica.requisitaDetalhes";

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Aguarde...");
        progressDialog.show();

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Formata retorno obtido do web-service (padrão parsing JSON)
                String str1 =  "{\"detalhes\":" + response.toString().substring(63);
                int tamanho=str1.length() -9 ;
                String str2 = str1.substring(0,tamanho) + "}";

                //envia retorno formatado para processo de Parsing
                ParseDetalhes pj = new ParseDetalhes(str2);
                pj.parseDetalhes();

                txtNome.setText(ParseDetalhes.Campo1);
                txtBairro.setText(ParseDetalhes.Campo2);
                txtEnderec.setText(ParseDetalhes.Campo3);
                txtPref.setText(ParseDetalhes.Campo4);
                txtCidade.setText(ParseDetalhes.Campo5);
                txtTelefone.setText("Telefone: " + ParseDetalhes.Campo6);
                txtObs.setText(ParseDetalhes.Campo7);

                MapLat = ParseDetalhes.Campo8;
                MapLongt = ParseDetalhes.Campo9;

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

    public void btMapaDetalhe(View view){

        //transferencia de dados entre Activitys
        Bundle b = new Bundle();
        b.putString("MapLatitude",MapLat);
        b.putString("MapLongitude",MapLongt);

        //inicia nova Activity
        Intent proximatela = new Intent(getApplicationContext(),MapsDetalhe.class);
        proximatela.putExtras(b);
        startActivity(proximatela);

    }

}