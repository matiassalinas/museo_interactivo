package io.github.matiassalinas.museo_interactivo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matias on 04-05-17.
 */

public class ObjetivosAdapter extends ArrayAdapter<Objetivo> {
    Context context;
    ArrayList<Objetivo> objetivos;
    public ObjetivosAdapter(Context context, ArrayList<Objetivo> objetivos){
        super(context,0,objetivos);
        this.context = context;
        this.objetivos = objetivos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Objetivo objetivo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_objetivo, parent, false);
        }
        TextView objetivoTitulo = (TextView) convertView.findViewById(R.id.objetivoTitulo);
        TextView objetivoPuntaje = (TextView) convertView.findViewById(R.id.objetivoPuntaje);

        objetivoTitulo.setText(objetivo.getTitulo());
        objetivoPuntaje.setText("0/0");
        return convertView;
    }
}
