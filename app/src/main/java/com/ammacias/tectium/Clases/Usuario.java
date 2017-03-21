package com.ammacias.tectium.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by macias on 21/03/2017.
 */

public class Usuario {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellidos")
    @Expose
    private String apellidos;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("token_id")
    @Expose
    private String tokenId;
    @SerializedName("id_facebook")
    @Expose
    private String idFacebook;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("sexo")
    @Expose
    private String sexo;
    @SerializedName("cumpleanos")
    @Expose
    private String cumpleanos;
    @SerializedName("admin")
    @Expose
    private String admin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(String cumpleanos) {
        this.cumpleanos = cumpleanos;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", mail='" + mail + '\'' +
                ", tokenId='" + tokenId + '\'' +
                ", idFacebook='" + idFacebook + '\'' +
                ", foto='" + foto + '\'' +
                ", sexo='" + sexo + '\'' +
                ", cumpleanos='" + cumpleanos + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
