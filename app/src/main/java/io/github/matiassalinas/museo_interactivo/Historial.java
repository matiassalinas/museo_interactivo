package io.github.matiassalinas.museo_interactivo;

/**
 * Created by matias on 05-05-17.
 */

public class Historial {
    private int idObjetivo, puntaje, idZona;

    public Historial(int idObjetivo, int puntaje, int idZona) {
        this.idObjetivo = idObjetivo;
        this.puntaje = puntaje;
        this.idZona = idZona;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
