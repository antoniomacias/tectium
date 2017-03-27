package com.ammacias.tectium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ammacias.tectium.Clases.Categorias;
import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Clases.Usuario;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.ammacias.tectium.Interfaces.ITectium;
import com.ammacias.tectium.Utils.Application_vars;
import com.ammacias.tectium.localdb.CategoriaDB;
import com.ammacias.tectium.localdb.CategoriaDBDao;
import com.ammacias.tectium.localdb.DatabaseConnection;
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

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity{

    EditText user, password;
    Button iniciar;

    //Facebook
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    String id_facebook, token_id;
    String nombre, apellidos, mail, sexo, cumpleanos, foto;

    //@usuarioExiste
    boolean bandera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        iniciar = (Button) findViewById(R.id.iniciarSesion);



        //TODO: QUITAR CUANDO FUNCIONE EL LOGIN BIEN
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean first_run = false;
                SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                first_run = settings.getBoolean("FIRST_RUN", false);
                if (!first_run) { // do the thing for the first time


                    System.out.println("*****************************\n****************************\nENTRO POR PRIMERA VEZ");
                    getCategorias();


                    settings = getSharedPreferences("PREFS_NAME", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("FIRST_RUN", true);
                    editor.commit();
                } else { // other time your app loads
                    System.out.println("Ya no descargo");
                }

                Intent i = new Intent(MainActivity.this, TabsActivity.class);
                startActivity(i);
            }
        });
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


                        String idSharedPreferences = "";
                        SharedPreferences settings = getSharedPreferences("PREFS_FACEBOOK", 0);
                        idSharedPreferences = settings.getString("ID_FACEBOOK", "N");

                        System.out.println("La prefs de face id es: "+idSharedPreferences);
                        System.out.println("El id que acaba de devolver Facebook es: "+id_facebook);

                        //TODO: Hago lo mismo en la primera como en el cualquier vez :S
                        //Primera vez
                        if (idSharedPreferences.equals("N") || id_facebook.equals("")) {
                            System.out.println("*****************************\n****************************\nLOGIN POR PRIMERA VEZ");


                            //Busco que no exista
                            //TODO: Petición retrofit que devuelve si existe el registro con id_facebook

                            //boolean bandera = peticion


                            //Si no existe: Creo al usuario
                            if (!usuarioExiste()){
                                crearUsuario();
                            }

                            //Creo el ID de las SharedPreferences
                            settings = getSharedPreferences("PREFS_FACEBOOK", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("ID_FACEBOOK", id_facebook);
                            editor.commit();
                        }else{

                            System.out.println("No es la primera vez");
                            //TODO: Si el id de SharedPrefs != id_facebook
                            //TODO: Petición retrofit que devuelve si existe el registro con id_facebook

                            //Si no existe: Creo al usuario
                            if (!usuarioExiste()){
                                System.out.println("Usuario no existe");
                                crearUsuario();
                            }else{
                                System.out.println("Usuario existe");
                            }

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

        if (resultCode == 0){

        }else if (resultCode == -1){
            Intent i = new Intent(MainActivity.this, TabsActivity.class);
            startActivity(i);
        }
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
                dameUsuario();
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al crear el usuario: "+t.getMessage());
            }
        });
    }

    private void dameUsuario() {

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit1.create(IRetrofit.class).getUsuarioToken(token_id).enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    if (response.body().getId()!=null){
                        Usuario current_user = new Usuario(response.body().getId(), response.body().getNombre(),
                                response.body().getApellidos(),response.body().getMail(), response.body().getTokenId(),
                                response.body().getIdFacebook(), response.body().getFoto(), response.body().getSexo(),
                                response.body().getCumpleanos(), response.body().getAdmin());
                        ((Application_vars) getApplication()).setUsuario(current_user);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al crear el usuario: "+t.getMessage());
            }
        });
    }

    public boolean usuarioExiste(){
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit1.create(IRetrofit.class).getUsuario(id_facebook).enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    bandera = true;
                    System.out.println("Existe el usuario");
                }else{
                    System.out.println("NO existe el usuario");
                    bandera = false;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error: "+t.getMessage());
            }
        });
        return bandera;
    }

    public void getCategorias() {

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit1.create(IRetrofit.class).getCategorias().enqueue(new Callback<Categorias>() {
            @Override
            public void onResponse(Response<Categorias> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Categorias r = response.body();
                    CategoriaDBDao categoriaDBDao= DatabaseConnection.getCategoriaDBDao(MainActivity.this);

                    for (CategoriaDB c: r.getData()){
                        CategoriaDB m = new CategoriaDB();
                        m.setId(c.getId());
                        m.setNombre(c.getNombre());
                        m.setDescripcion(c.getDescripcion());
                        m.setTag(c.getTag());

                        categoriaDBDao.insertOrReplace(m);
                    }
                    System.out.println("*****************CategoriasDBDAO ALL***********\n"+categoriaDBDao.loadAll());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
