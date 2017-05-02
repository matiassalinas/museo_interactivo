package io.github.matiassalinas.museo_interactivo;

import java.util.ArrayList;

/**
 * Created by matias on 02-05-17.
 */

public class Museo {
    private int idMuseo;
    private String nombre, correo, direccion, telefono;
    private ArrayList<Zona> zonas;

    public Museo(int idMuseo, String nombre, String correo, String direccion, String telefono, ArrayList<Zona> zonas) {
        this.idMuseo = idMuseo;
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.zonas = zonas;
    }

    public int getIdMuseo() {
        return idMuseo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public ArrayList<Zona> getZonas() {
        return zonas;
    }

    public void addZona(Zona zona) {
        this.zonas.add(zona);
    }
}
