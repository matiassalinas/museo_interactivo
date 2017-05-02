package io.github.matiassalinas.museo_interactivo;

/**
 * Created by matias on 02-05-17.
 */

public class Zona {
    private int idZona, idMuseo;
    private String nombre, descripcion, imagen;

    public Zona(int idZona, int idMuseo, String nombre, String descripcion, String imagen) {
        this.idZona = idZona;
        this.idMuseo = idMuseo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public int getIdMuseo() {
        return idMuseo;
    }

    public void setIdMuseo(int idMuseo) {
        this.idMuseo = idMuseo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
