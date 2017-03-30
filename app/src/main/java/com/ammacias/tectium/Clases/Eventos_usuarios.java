package com.ammacias.tectium.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ammacias on 27/03/2017.
 */

public class Eventos_usuarios {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Evento_usuario> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Evento_usuario> getData() {
        return data;
    }

    public void setData(List<Evento_usuario> data) {
        this.data = data;
    }
}
