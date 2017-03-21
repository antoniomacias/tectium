package com.ammacias.tectium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ammacias.tectium.Clases.Usuario;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText user, password;

    //Facebook
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    String id_facebook, token_id;
    String nombre, apellidos, mail, sexo, cumpleanos, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        loginButton = (LoginButton)findViewById(R.id.login_button);


        // Le damos permisos específicos para almacenar su email. La app le avisará automáticamente.
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday")); //, "user_birthday", "user_friends"

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //requestUserProfile(loginResult);

                id_facebook = loginResult.getAccessToken().getUserId();
                token_id= loginResult.getAccessToken().getToken();

                String accessToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);


                        nombre = (String) bFacebookData.get("first_name");
                        apellidos = (String) bFacebookData.get("last_name");
                        mail = (String) bFacebookData.get("email");
                        sexo = (String) bFacebookData.get("gender");
                        cumpleanos = (String) bFacebookData.get("birthday");

                        System.out.println("Nombre: "+nombre+"\nApellidos: "+apellidos+"\nEmail: "+mail
                                +"\nSexo: "+sexo+"\nCumpleaños: "+cumpleanos);

                        //crearUsuario();

                        String idSharedPreferences = "";
                        SharedPreferences settings = getSharedPreferences("PREFS_FACEBOOK", 0);
                        idSharedPreferences = settings.getString("ID_FACEBOOK", "N");

                        System.out.println("La prefs de face id es: "+idSharedPreferences);
                        System.out.println("El id que acaba de devolver Facebook es: "+id_facebook);

                        //TODO: Hago lo mismo en la primera como en el cualquier vez :S
                        //Primera vez
                        if (idSharedPreferences.equals("N") || id_facebook.equals("")) {
                            System.out.println("*****************************\n****************************\nLOGIN POR PRIMERA VEZ");
                            boolean bandera = false;

                            //Busco que no exista
                            //TODO: Petición retrofit que devuelve si existe el registro con id_facebook
                            //boolean bandera = peticion


                            //Si no existe: Creo al usuario
                            if (!bandera) {
                                crearUsuario();
                            }

                            //Creo el ID de las SharedPreferences
                            settings = getSharedPreferences("PREFS_FACEBOOK", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("ID_FACEBOOK", id_facebook);
                            editor.commit();
                        }else{

                            //TODO: Si el id de SharedPrefs != id_facebook
                            //TODO: Petición retrofit que devuelve si existe el registro con id_facebook

                            settings = getSharedPreferences("PREFS_FACEBOOK", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("ID_FACEBOOK", id_facebook);
                            editor.commit();

                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException e) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
                foto = profile_pic.toString();
                System.out.println("Foto: "+foto);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        }
        catch(JSONException e) {
            System.out.println("Error parsing JSON");
        }
        return null; //¿?
    }

    public void crearUsuario(){
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit1.create(IRetrofit.class).createUser(nombre, apellidos, mail,
                token_id, id_facebook, foto, sexo, cumpleanos ).enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                System.out.println("Exito al crear el usuario");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("    Error al crear el usuario: "+t.getMessage());
            }
        });
    }
}
