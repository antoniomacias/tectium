package com.ammacias.tectium.Interfaces;

import com.ammacias.tectium.Clases.Eventos;
import com.ammacias.tectium.Clases.Usuario;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
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

    @GET("usuario")
    Call<Usuario> getUsuario(@Query("id") String id);

    @GET("eventos")
    Call<Eventos> getFullEvents();

}
