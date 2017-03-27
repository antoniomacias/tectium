package com.ammacias.tectium.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ammacias on 27/03/2017.
 */

public class Evento_usuario {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_usuario")
    @Expose
    private String idUsuario;
    @SerializedName("id_evento")
    @Expose
    private String idEvento;
    @SerializedName("fav")
    @Expose
    private String fav;
    @SerializedName("leido")
    @Expose
    private String leido;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getLeido() {
        return leido;
    }

    public void setLeido(String leido) {
        this.leido = leido;
    }
}
