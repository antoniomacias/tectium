package com.ammacias.tectium;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Clases.Usuario;
import com.ammacias.tectium.Fragments.EventoFragment;
import com.ammacias.tectium.Fragments.ListaEventosFragment;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.ammacias.tectium.Interfaces.ITectium;

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
                    f = new ListaEventosFragment();

                   /* Bundle args = new Bundle();
                    args.put("arg", TabsActivity.this);
                    f.setArguments(args);*/
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    /*f = new RespuestaFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();*/
                    return true;
                case R.id.navigation_notifications:
                    /*f = new ConfigFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, f)
                            .commit();*/
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
    public void onClickFav(Evento e) {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit1.create(IRetrofit.class).eventoFav().enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Response<Usuario> response, Retrofit retrofit) {
                System.out.println("Exito al crear el usuario");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al crear el usuario: "+t.getMessage());
            }
        });
    }
}
