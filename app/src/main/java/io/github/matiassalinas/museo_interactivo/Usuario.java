package io.github.matiassalinas.museo_interactivo;

import java.util.ArrayList;

/**
 * Created by matias on 05-05-17.
 */

public class Usuario {
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

    public int puntajeZona(int idZona){
        int contador = 0;
        for(int i = 0; i < historial.size(); i++){
            if(historial.get(i).getIdZona() == idZona) contador++;
        }
        return contador;
    }

    public int puntajeTotal(){
        int puntaje = 0;
        for(int i = 0; i< historial.size(); i++){
            puntaje+=historial.get(i).getPuntaje();
        }
        return puntaje;
    }


}
