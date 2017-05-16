package io.github.matiassalinas.museo_interactivo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matias on 03-05-17.
 */

public class ZonasAdapter extends ArrayAdapter<Zona> {
    Context context;
    ArrayList<Zona> zonas;
    ArrayList<Historial> historial;
    public ZonasAdapter(Context context, ArrayList<Zona> zonas, ArrayList<Historial> historial){
        super(context,0,zonas);
        this.context = context;
        this.zonas = zonas;
        this.historial = historial;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Zona zona = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_zona, parent, false);
        }
        TextView zonaName = (TextView) convertView.findViewById(R.id.zonaName);
        TextView zonaObjetivos = (TextView) convertView.findViewById(R.id.zonaObjetivos);

        zonaName.setText(zona.getNombre());
        int cantidad = 0;
        for(int i = 0; i < historial.size(); i++){
            if(historial.get(i).getIdZona() == zona.getIdZona()) cantidad++;
        }
        if(cantidad == zona.getContadorObjetivos()){
            convertView.setBackgroundColor(context.getColor(R.color.listOk));
        }
        zonaObjetivos.setText(cantidad+"/"+zona.getContadorObjetivos());
        return convertView;
    }

}
