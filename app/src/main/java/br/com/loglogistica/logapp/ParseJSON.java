package br.com.loglogistica.logapp;

/**
 * Created by Carlos Sales on 30/10/2016.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {

    public static final String JSON_ARRAY = "entregas";

    public static String[] IDs;
    public static String[] Titulos;
    public static String[] SubTitulos;

    // defina aqui os campos a serem lidos
    public static final String KEY_ID = "ID_Entrega";
    public static final String KEY_TITULO = "Bairro";
    public static final String KEY_SUBTITULO = "Endereco";

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

            Titulos = new String[users.length()];
            SubTitulos = new String[users.length()];
            IDs = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                Titulos[i] = jo.getString(KEY_TITULO);
                SubTitulos[i] = jo.getString(KEY_SUBTITULO);
                IDs[i] = jo.getString(KEY_ID);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}