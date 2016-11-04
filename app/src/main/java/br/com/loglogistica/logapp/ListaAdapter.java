package br.com.loglogistica.logapp;

/**
 * Created by Carlos Sales on 30/10/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//ArrayAdapter será responsável por administrar e retornar as Views para a nossa lista
public class ListaAdapter extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] bairros;
    private final String[] enderecos;

    //O construtor pode receber quantos parametros forem necessários mas um array de String deve ser passado como parametro do construtor da super-classe
    public ListaAdapter(Activity context, String[] bairros, String[] enderecos)
    {
        super(context, R.layout.lista_itens, bairros);
        this.context = context;
        this.bairros = bairros;
        this.enderecos = enderecos;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        //Aqui retornamos o layout para podermos administrar as Views da tela
        View rowView= inflater.inflate(R.layout.lista_itens, null, true);

        //---retorne a referencia de todos os objetos do layout
        TextView txtTitulo = (TextView) rowView.findViewById(R.id.txtLinguagens);
        TextView txtDescricao = (TextView)rowView.findViewById(R.id.txtDescricao);

        //---passe os textos baseados na posição atual do listView
        txtTitulo.setText(bairros[position]);
        txtDescricao.setText(enderecos[position]);
        return rowView;
    }
}