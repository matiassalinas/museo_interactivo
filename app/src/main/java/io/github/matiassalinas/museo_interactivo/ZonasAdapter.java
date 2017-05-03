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
    public ZonasAdapter(Context context, ArrayList<Zona> zonas){
        super(context,0,zonas);
        this.context = context;
        this.zonas = zonas;
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
        zonaObjetivos.setText("0/0");
        return convertView;
    }

}
