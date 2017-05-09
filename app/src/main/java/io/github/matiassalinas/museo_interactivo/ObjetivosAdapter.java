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
    ArrayList<Historial> historial;
    ArrayList<Integer> inactivos = new ArrayList<>();

    public ObjetivosAdapter(Context context, ArrayList<Objetivo> objetivos, ArrayList<Historial> historial){
        super(context,0,objetivos);
        this.context = context;
        this.objetivos = objetivos;
        this.historial = historial;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Objetivo objetivo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_objetivo, parent, false);
        }
        TextView objetivoTitulo = (TextView) convertView.findViewById(R.id.objetivoTitulo);
        TextView objetivoPuntaje = (TextView) convertView.findViewById(R.id.objetivoPuntaje);
        String puntaje = "?";
        for(int i = 0 ; i < historial.size() ; i++) {
            if(historial.get(i).getIdObjetivo() == objetivo.getIdObjetivo()) {
                puntaje = String.valueOf(historial.get(i).getPuntaje());
                inactivos.add(position);
                break;
            }
        }
        objetivoTitulo.setText(objetivo.getTitulo());
        objetivoPuntaje.setText(puntaje+"/100");
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        for(int i = 0; i < inactivos.size(); i++){ //Desactivo item que ya tiene puntaje.
            if(inactivos.get(i) == position) return false;
        }
        return true;
    }
}
