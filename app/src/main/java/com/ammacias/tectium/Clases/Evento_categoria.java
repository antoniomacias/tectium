package com.ammacias.tectium.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by macias on 28/03/2017.
 */

public class Evento_categoria {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_categoria")
    @Expose
    private String idCategoria;
    @SerializedName("id_evento")
    @Expose
    private String idEvento;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }
}
