package com.ammacias.tectium;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Clases.Evento_usuario;
import com.ammacias.tectium.Clases.Example;
import com.ammacias.tectium.Fragments.DetalleEventoFragment;
import com.ammacias.tectium.Fragments.NewEventoFragment;
import com.ammacias.tectium.Recycler.EventoFragment;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.ammacias.tectium.Interfaces.ITectium;
import com.ammacias.tectium.Recycler.OwnEventFragment;
import com.ammacias.tectium.Utils.Application_vars;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class TabsActivity extends AppCompatActivity implements ITectium{

    //Fragment
    Fragment f;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    f = new EventoFragment();

                   /* Bundle args = new Bundle();
                    args.put("arg", TabsActivity.this);
                    f.setArguments(args);*/
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    f = new OwnEventFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    f = new OwnEventFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        f = new EventoFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, f)
                .commit();
    }


    @Override
    public void onClickEvento(Evento e) {
        System.out.println("Evento: "+e.toString());
    }

    @Override
    public void onClickOwnEvento(Evento e) {
        f = new DetalleEventoFragment();
        Bundle args = new Bundle();
        args.putParcelable("evento", Parcels.wrap(e));
        f.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, f)
                .commit();
    }

    @Override
    public void onClickFav(Evento e) {

        //TODO: Si existe el registro en la tabla usuario_evento hago update sino inserto
        existeRegistroDelEvento(e.getId());

    }

    @Override
    public void onClickShare(Evento e) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Tectium");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "El usuario "+ ((Application_vars) getApplication()).getUsuario().getNombre()
                +" "+ ((Application_vars) getApplication()).getUsuario().getApellidos()+" ha compartido un evento.\n"
        + "Has sido invitado al evento " + e.getNombre()+ " celebrado el "+e.getFecha()+" en " + e.getSitio()+"\n."
        + e.getDescripcion()+".\nEl precio es de + "+e.getPrecio()+".\nEsperamos verte!!");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Elige la aplicación a compartir"));
        }

    private void existeRegistroDelEvento(final String idE) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //TODO: No es 1, es usuario.getId() de APPLICATION
        retrofit1.create(IRetrofit.class).getOneEventUsuario(((Application_vars)getApplication()).getUsuario().getId(), idE).enqueue(new Callback<Evento_usuario>() {

            @Override
            public void onResponse(Response<Evento_usuario> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    System.out.println(response.body().getIdEvento());
                    System.out.println(response.body().getFav());
                    System.out.println(response.body().getIdUsuario());
                   if (response.body().getId()!=null){
                       updateEventoUsuario(idE, response.body().getFav());
                   }else{
                       crearEventoUsuario(idE);
                   }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al encontrar el evento: "+t.getMessage());
            }
        });
    }

    private void updateEventoUsuario(String idE, String fav) {
        String favoritoFinal;
        if (Integer.parseInt(fav)==0){
            favoritoFinal = "1";
        }else{
            favoritoFinal = "0";
        }

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //TODO: No es 1, es usuario.getId() de APPLICATION
        retrofit1.create(IRetrofit.class).editarFavEventoUsuario(((Application_vars)getApplication()).getUsuario().getId(),idE, favoritoFinal).enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    System.out.println("Exito al cambiar de fav el evento");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al favear el evento: "+t.getMessage());
            }
        });

    }

    private void crearEventoUsuario(String idE) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //TODO: No es 1, es usuario.getId() de APPLICATION
        retrofit1.create(IRetrofit.class).crearEventoFav(((Application_vars)getApplication()).getUsuario().getId(),idE, "1", "0").enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                if (response.isSuccess()){

                    System.out.println("Exito al favear el evento");

                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al favear el evento: "+t.getMessage());
            }
        });
    }

    public void addEvento(View view) {
        f = new NewEventoFragment();
        /*Bundle args = new Bundle();
        args.putParcelable("evento", Parcels.wrap(e));
        f.setArguments(args);*/

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, f)
                .commit();
    }
}
