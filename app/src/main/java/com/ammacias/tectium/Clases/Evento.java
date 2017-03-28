package com.ammacias.tectium.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by macias on 21/03/2017.
 */
@Parcel
public class Evento {
    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("nombre")
    @Expose
    String nombre;
    @SerializedName("sitio")
    @Expose
    String sitio;
    @SerializedName("descripcion")
    @Expose
    String descripcion;
    @SerializedName("fecha")
    @Expose
    String fecha;
    @SerializedName("foto")
    @Expose
    String foto;
    @SerializedName("precio")
    @Expose
    String precio;


    public Evento() {
    }

    public Evento(String id, String nombre, String sitio, String descripcion, String fecha, String foto, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.sitio = sitio;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.foto = foto;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", sitio='" + sitio + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                ", foto=" + foto +
                ", precio='" + precio + '\'' +
                '}';
    }
}
