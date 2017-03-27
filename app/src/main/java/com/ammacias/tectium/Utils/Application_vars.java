package com.ammacias.tectium.Utils;

import android.app.Application;

import com.ammacias.tectium.Clases.Usuario;

/**
 * Created by ammacias on 27/03/2017.
 */

public class Application_vars extends Application {
    Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
