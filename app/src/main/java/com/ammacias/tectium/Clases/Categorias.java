package com.ammacias.tectium.Clases;

import com.ammacias.tectium.localdb.CategoriaDB;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ammacias on 23/03/2017.
 */

public class Categorias {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<CategoriaDB> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CategoriaDB> getData() {
        return data;
    }

    public void setData(List<CategoriaDB> data) {
        this.data = data;
    }

}
