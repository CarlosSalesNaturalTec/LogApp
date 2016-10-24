package br.com.loglogistica.logapp;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    String[] Linguagens = {
            "Barra",
            "Barra",
            "Ondina",
            "Pituba",
            "Pituba",
            "Rio Vermelho",
            "Rio Vermelho",
            "Pituba",
            "Pituba",
            "Rio Vermelho",
            "Rio Vermelho",
            "Pituba",
            "Pituba",
            "Rio Vermelho",
            "Rio Vermelho"
    };


    String[] Descricao= {
            "Av. Sete de Setembro, 3937 - ANTONIO CARLOS DE SOUZA",
            "Marquês de Caravelas, 123 - ANTONIO PAULO DOS SANTOS",
            "Rua das Amélias, 432 - MARIA DE JESUS SILVA",
            "Trav. Santo Amaro, 323, 2o Andar - PAULO EMILIO QUEIROZ",
            "Rua Mesquita, 50, Térreo - MARCOS ANTONIO DA SILVA",
            "Rua A, Quadra 32, LOte 24 - JOÃO AMÉRICO DE PÁDUA",
            "Trav. das Bromélias, 432A - IGOR CAIO DE FARIAS",
            "Trav. Santo Amaro, 323, 2o Andar - PAULO EMILIO QUEIROZ",
            "Rua Mesquita, 50, Térreo - MARCOS ANTONIO DA SILVA",
            "Rua A, Quadra 32, LOte 24 - JOÃO AMÉRICO DE PÁDUA",
            "Trav. das Bromélias, 432A - IGOR CAIO DE FARIAS",
            "Trav. Santo Amaro, 323, 2o Andar - PAULO EMILIO QUEIROZ",
            "Rua Mesquita, 50, Térreo - MARCOS ANTONIO DA SILVA",
            "Rua A, Quadra 32, LOte 24 - JOÃO AMÉRICO DE PÁDUA",
            "Trav. das Bromélias, 432A - IGOR CAIO DE FARIAS"
    };

    Integer[] imageIDs = {
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador,
            R.drawable.marcador
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Monta Lista de Entregas
        CustomLista lista = new CustomLista(this,Linguagens ,imageIDs ,Descricao);
        setListAdapter(lista);

        //backgroud Menu
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.bg));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);

        MenuItem op1 = menu.add(0,0,0,"Atualizar");
        op1.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        MenuItem op2 = menu.add(0,1,1,"Configurações");
        op2.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int panel, MenuItem item){
        switch (item.getItemId()){
            case 0 :
                Toast.makeText(this, "Atualizado com Sucesso!" , Toast.LENGTH_LONG).show();
                break;

            case 1 :
                Toast.makeText(this, "Configurações Salvas." , Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Pegar o item clicado
        //Object o = this.getListAdapter().getItem(position);
        //String lselecao = o.toString();
        //Apresentar o item clicado
        //Toast.makeText(this, "Você clicou na Entrega : " + lselecao, Toast.LENGTH_LONG).show();

        Intent secondActivity = new Intent(this, MapsActivity.class);
        startActivity(secondActivity);

    }

}