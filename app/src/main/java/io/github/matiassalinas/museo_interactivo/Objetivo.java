package io.github.matiassalinas.museo_interactivo;

import java.io.Serializable;

/**
 * Created by matias on 02-05-17.
 */

public class Objetivo implements Serializable {
    private int idObjetivo, idZona;
    private String titulo, texto,imagen;

    public Objetivo(int idObjetivo, int idZona, String titulo, String texto, String imagen) {
        this.idObjetivo = idObjetivo;
        this.idZona = idZona;
        this.titulo = titulo;
        this.texto = texto;
        this.imagen = imagen;
    }

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
