package com.ammacias.tectium.Interfaces;

import com.ammacias.tectium.Clases.Categorias;
import com.ammacias.tectium.Clases.Evento_usuario;
import com.ammacias.tectium.Clases.Eventos;
import com.ammacias.tectium.Clases.Eventos_categorias;
import com.ammacias.tectium.Clases.Eventos_usuarios;
import com.ammacias.tectium.Clases.Example;
import com.ammacias.tectium.Clases.Usuario;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by macias on 21/03/2017.
 */

public interface IRetrofit {
    String ENDPOINT = "http://www.antoniomacias.esy.es/api.php/";

    @FormUrlEncoded
    @POST("usuario")
    Call<Usuario> createUser(@Field("nombre") String nombre, @Field("apellidos") String apellidos,
                             @Field("mail") String email, @Field("token_id") String token_id,
                             @Field("id_facebook") String id_facebook, @Field("foto") String foto,
                             @Field("sexo") String sexo, @Field("cumpleanos") String cumpleanos);

    @GET("usuario/{id}")
    Call<Usuario> getUsuario(@Path("id") String idFace);

    @GET("eventos")
    Call<Eventos> getFullEvents();

    //GET eventos por idUser
    @GET("eventosOwn/{id}")
    Call<Eventos> getOwnEvents(@Path("id") String id);

    @GET("categorias")
    Call<Categorias> getCategorias();

    //Crear un Evento_Usuario
    @FormUrlEncoded
    @POST("evento_usuario")
    Call<Example> crearEventoFav(@Field("id_usuario") String id_usuario, @Field("id_evento") String id_evento,
                            @Field("fav") String fav, @Field("leido") String leido);

    //GET un Evento_Usuario
    @GET("evento_usuario_item")
    Call<Evento_usuario> getOneEventUsuario(@Query("id_usuario") String id_usuario,@Query("id_evento") String id_evento);

    //Editar FAV un Evento_Usuario
    @FormUrlEncoded
    @POST("evento_usuario_fav")
    Call<Example> editarFavEventoUsuario(@Field("id_usuario") String id_usuario, @Field("id_evento") String id_evento,
                                 @Field("fav") String fav);

    //Usuario por token
    @GET("usuariotoken/{idtoken}")
    Call<Usuario> getUsuarioToken(@Path("idtoken") String idtoken);

    //Login mail usuario
    @GET("usuariomail/{mail}")
    Call<Usuario> getUsuarioMail(@Path("mail") String mail);

    //Crear un Evento
    @FormUrlEncoded
    @POST("evento")
    Call<Example> crearEvento(@Field("nombre") String nombre, @Field("sitio") String sitio,
                              @Field("descripcion") String descripcion, @Field("fecha") String fecha,
                              @Field("foto") String foto, @Field("precio") String precio,@Field("id_creador") String id_creador);

    //Categorias de un evento
    @GET("evento_categoriacat/{id}")
    Call<Eventos_categorias> getCatFromEvent(@Path("id") String id);


}
