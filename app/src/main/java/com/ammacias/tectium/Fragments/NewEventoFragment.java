package com.ammacias.tectium.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.ammacias.tectium.Clases.Example;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.ammacias.tectium.NewEventoActivity;
import com.ammacias.tectium.R;
import com.ammacias.tectium.Utils.Application_vars;
import com.frosquivel.magicalcamera.MagicalPermissions;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewEventoFragment extends Fragment {

    //Camera
    private MagicalPermissions magicalPermissions;

    final String[] curDate = new String[1];
    CalendarView calendarView;
    EditText nombre, descripcion, precio, dateEvento, timeEvento;
    Button b;

    public NewEventoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_evento, container, false);

        nombre = (EditText)v.findViewById(R.id.nombre);
        descripcion = (EditText)v.findViewById(R.id.descripcion);
        precio = (EditText)v.findViewById(R.id.precio);
        dateEvento = (EditText)v.findViewById(R.id.dateEvento);
        timeEvento = (EditText)v.findViewById(R.id.timeEvento);
        b = (Button) v.findViewById(R.id.addEvento);

        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        magicalPermissions = new MagicalPermissions(this, permissions);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println(String.valueOf(calendarView.getDate()));
                System.out.println("aaa");
                Retrofit retrofit1 = new Retrofit.Builder()
                        .baseUrl(IRetrofit.ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //TODO: SITIO Y FOTO
                retrofit1.create(IRetrofit.class).crearEvento(nombre.getText().toString(),"",descripcion.getText().toString(), dateEvento.getText().toString() +" "+
                        timeEvento.getText().toString(), " ",precio.getText().toString(),((Application_vars)getActivity().getApplication()).getUsuario().getId()).enqueue(new Callback<Example>() {

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
        });
        return v;
    }
    /*public void chooseDateTime(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.fragment_datetime_new_evento, null);


        final String[] fecha = new String[1];
        calendarView = (CalendarView)view.findViewById(R.id.calendarView);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                System.out.println(("Date is : " + dayOfMonth +" / " + (month+1) + " / " + year));

            }
        });



        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();



        // Cuando cierre el diálogo, redireccionar a las categorías
        /*dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                redirect(findViewById(R.id.activity));
            }
        });
    }*/

}
