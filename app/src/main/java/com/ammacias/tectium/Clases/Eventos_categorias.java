package com.ammacias.tectium.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by macias on 28/03/2017.
 */

public class Eventos_categorias {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Evento_categoria> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Evento_categoria> getData() {
        return data;
    }

    public void setData(List<Evento_categoria> data) {
        this.data = data;
    }
}
