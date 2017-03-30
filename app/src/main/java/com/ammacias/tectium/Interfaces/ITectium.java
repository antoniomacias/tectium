package com.ammacias.tectium.Interfaces;

import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Clases.Evento_usuario;

/**
 * Created by macias on 21/03/2017.
 */

public interface ITectium {
    void onClickEvento(Evento e);
    void onClickOwnEvento(Evento e);
    void onClickFav(Evento e);
    void onClickShare(Evento e);
    void onClickFavoritoRecycler(Evento_usuario e);
    void onClickFavFav(Evento_usuario e);
    void onClickShareFav(Evento_usuario e);
}
