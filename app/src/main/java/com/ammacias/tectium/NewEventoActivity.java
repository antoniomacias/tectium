package com.ammacias.tectium;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.ammacias.tectium.Clases.Example;
import com.ammacias.tectium.Clases.Usuario;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.ammacias.tectium.Utils.Application_vars;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class NewEventoActivity extends AppCompatActivity {

    final String[] curDate = new String[1];
    CalendarView calendarView;
    EditText nombre, descripcion, precio, dateEvento, timeEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_evento);

        nombre = (EditText)findViewById(R.id.nombre);
        descripcion = (EditText)findViewById(R.id.descripcion);
        precio = (EditText)findViewById(R.id.precio);
        dateEvento = (EditText)findViewById(R.id.dateEvento);
        timeEvento = (EditText)findViewById(R.id.timeEvento);
    }

    public void chooseDateTime(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewEventoActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.fragment_datetime_new_evento, null);


        final String[] fecha = new String[1];
        calendarView = (CalendarView)view.findViewById(R.id.calendarView);


        /*calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                System.out.println(("Date is : " + dayOfMonth +" / " + (month+1) + " / " + year));

            }
        });*/



        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();



        // Cuando cierre el diálogo, redireccionar a las categorías
        /*dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                redirect(findViewById(R.id.activity));
            }
        });*/
    }

    public void addEvento(View view) {
        //System.out.println(String.valueOf(calendarView.getDate()));
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //TODO: SITIO Y FOTO
        retrofit1.create(IRetrofit.class).crearEvento(nombre.getText().toString(),"",descripcion.getText().toString(), dateEvento.getText().toString() +" "+
                timeEvento.getText().toString(), " ",precio.getText().toString(),((Application_vars)getApplication()).getUsuario().getId()).enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    if (response.body().getMessage().equalsIgnoreCase("success")){
                        System.out.println("Evento creado");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al crear el usuario: "+t.getMessage());
            }
        });
    }


}
