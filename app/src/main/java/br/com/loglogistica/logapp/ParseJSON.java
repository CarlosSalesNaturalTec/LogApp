package br.com.loglogistica.logapp;

/**
 * Created by Carlos Sales on 30/10/2016.
 */

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {

    public static String[] Nomes;
    public static String[] Enderecos;
    public static String[] PontoRefs;
    public static String[] Bairros;
    public static String[] Cidades;
    public static String[] Telefones;
    public static String[] Obsvs;
    public static String[] Latitudes;
    public static String[] Longitudes;

    public static final String JSON_ARRAY = "return";

    public static final String KEY_NOME = "Nome";
    public static final String KEY_ENDERECO = "Endereco";
    public static final String KEY_PONTOREF = "Ponto_Ref";
    public static final String KEY_BAIRRO = "Bairro";
    public static final String KEY_CIDADE = "Cidade";
    public static final String KEY_TELEFONE = "Telefone";
    public static final String KEY_OBS = "Observacoes";
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";

    private JSONArray users = null;
    private String json;
    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){

        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            Nomes = new String[users.length()];
            Enderecos = new String[users.length()];
            PontoRefs = new String[users.length()];
            Bairros = new String[users.length()];
            Cidades = new String[users.length()];
            Telefones = new String[users.length()];
            Obsvs = new String[users.length()];
            Latitudes = new String[users.length()];
            Longitudes = new String[users.length()];

            for(int i=0;i<users.length();i++){

                JSONObject jo = users.getJSONObject(i);

                Nomes[i] = jo.getString(KEY_NOME);
                Enderecos[i] = jo.getString(KEY_ENDERECO);
                PontoRefs[i] = jo.getString(KEY_PONTOREF);
                Bairros[i] = jo.getString(KEY_BAIRRO);
                Cidades[i] = jo.getString(KEY_CIDADE);
                Telefones[i] = jo.getString(KEY_TELEFONE);
                Obsvs[i] = jo.getString(KEY_OBS);
                Latitudes[i] = jo.getString(KEY_LATITUDE);
                Longitudes[i] = jo.getString(KEY_LONGITUDE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}