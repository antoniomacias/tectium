package com.ammacias.tectium.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ammacias on 27/03/2017.
 */

public class Evento_usuario {
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("idU")
    @Expose
    private String idU;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sitio")
    @Expose
    private String sitio;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("foto")
    @Expose
    private Object foto;
    @SerializedName("precio")
    @Expose
    private String precio;
    @SerializedName("fav")
    @Expose
    private String fav;
    @SerializedName("idEU")
    @Expose
    private String idEU;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
        this.idU = idU;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Object getFoto() {
        return foto;
    }

    public void setFoto(Object foto) {
        this.foto = foto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getIdEU() {
        return idEU;
    }

    public void setIdEU(String idEU) {
        this.idEU = idEU;
    }
}
