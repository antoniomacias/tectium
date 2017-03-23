package com.ammacias.tectium;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Fragments.EventoFragment;
import com.ammacias.tectium.Interfaces.ITectium;

public class TabsActivity extends AppCompatActivity implements ITectium{

    private TextView mTextMessage;
    //Fragment
    Fragment f;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    f = new EventoFragment();
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

        mTextMessage = (TextView) findViewById(R.id.message);
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
}
