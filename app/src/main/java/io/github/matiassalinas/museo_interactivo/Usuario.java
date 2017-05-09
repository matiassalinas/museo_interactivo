package io.github.matiassalinas.museo_interactivo;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by matias on 05-05-17.
 */

public class Usuario implements Serializable {
    private String idEntrada, idUsuario;
    private ArrayList<Historial> historial;

    public Usuario(String idEntrada, String idUsuario, ArrayList<Historial> historial) {
        this.idEntrada = idEntrada;
        this.idUsuario = idUsuario;
        if(historial == null) historial = new ArrayList<>();
        else this.historial = historial;
    }

    public String getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(String idEntrada) {
        this.idEntrada = idEntrada;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ArrayList<Historial> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Historial> historial) {
        this.historial = historial;
    }

    public int puntajeTotal(){
        int puntaje = 0;
        for(int i = 0; i< historial.size(); i++){
            puntaje+=historial.get(i).getPuntaje();
        }
        return puntaje;
    }

    public String getRango( Context context, int cantObjetivos){
        //int max = cantObjetivos*100;
        //int min = cantObjetivos*20;
        int puntaje = puntajeTotal();
        String rango;
        if(puntaje<cantObjetivos*10){
            rango = context.getString(R.string.Bronce);
        }
        else if(puntaje<cantObjetivos*20){
            rango = context.getString(R.string.Plata);
        }
        else if(puntaje<cantObjetivos*50){
            rango = context.getString(R.string.Oro);
        }
        else if(puntaje<cantObjetivos*70){
            rango = context.getString(R.string.Platino);
        }
        else if(puntaje<cantObjetivos*90){
            rango = context.getString(R.string.Diamante);
        }
        else{
            rango = context.getString(R.string.Leyenda);
        }
        return rango;
    }


}
