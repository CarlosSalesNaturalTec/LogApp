package br.com.loglogistica.logapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends Activity {

    EditText editID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        editID = (EditText) findViewById(R.id.editIDConfig);
    }

    public void SalvarID(View view){

        SharedPreferences preferences = getSharedPreferences("LOG_CONFIG", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putString("IDMotoboy",editID.getText().toString());
        editor.commit();

        Global.globalID = editID.getText().toString();
        Toast.makeText(getApplicationContext(), "ID Salvo:"+ Global.globalID , Toast.LENGTH_SHORT).show();

    }

}
