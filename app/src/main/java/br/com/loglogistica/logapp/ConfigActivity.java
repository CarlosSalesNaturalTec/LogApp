package br.com.loglogistica.logapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConfigActivity extends Activity {

    EditText editID,editsenha;
    DateFormat dateFormat,horaFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        editID = (EditText) findViewById(R.id.editIDConfig);
        editsenha = (EditText) findViewById(R.id.editSenha);
    }

    public void SalvarID(View view) throws IOException {

        String senha = editsenha.getText().toString();

        switch (senha){
            case "271216" :

                // salva ID em arquivo de preferências compartilhadas
                //=================================================================================================
                SharedPreferences preferences = getSharedPreferences("LOG_CONFIG", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = preferences.edit();
                editor.putString("IDMotoboy",editID.getText().toString());
                editor.commit();

                Global.globalID = editID.getText().toString();


                //Criação de Arquivo de LOG
                //=================================================================================================
                dateFormat = new SimpleDateFormat("dd_MM_yy");
                horaFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                try {
                    String string = horaFormat.format(date) + "hs_" + "IDConfigurado" + "\n";
                    FileOutputStream fos = openFileOutput("LOG_Registro", Context.MODE_PRIVATE);
                    fos.write(string.getBytes());
                    fos.close();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Não foi possivel gravar arquivo de LOG", Toast.LENGTH_SHORT).show();
                }



                Toast.makeText(getApplicationContext(), "ID Salvo:"+ Global.globalID + ". Reiniciar Aplicativo", Toast.LENGTH_SHORT).show();
                break;

        default :
            Toast.makeText(getApplicationContext(), "SENHA INCORRETA" , Toast.LENGTH_SHORT).show();
        }

    }

}
