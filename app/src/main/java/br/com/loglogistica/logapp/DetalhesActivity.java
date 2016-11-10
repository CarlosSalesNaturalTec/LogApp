package br.com.loglogistica.logapp;

import android.app.Activity;
import android.app.backup.SharedPreferencesBackupHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class DetalhesActivity extends Activity  {

    Button botaoConcluir;
    Button botaoMapa;
    Button botaoStartTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        //botões
        botaoConcluir = (Button) findViewById(R.id.btConcluida);
        botaoMapa = (Button) findViewById(R.id.btMap);
        botaoStartTravel = (Button) findViewById(R.id.btStart);

        //monta Spinner (combo com lista de opções)
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this,
                R.array.status_array, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Listner para seleção do usuario no Spinner
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

    }

}