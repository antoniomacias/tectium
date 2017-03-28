package com.ammacias.tectium.Recycler;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ammacias.tectium.Clases.Eventos;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.ammacias.tectium.Interfaces.ITectium;
import com.ammacias.tectium.R;
import com.ammacias.tectium.Utils.GPSTracker;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link com.ammacias.tectium.Interfaces.ITectium}
 * interface.
 */
public class EventoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ITectium mListener;
    RecyclerView recyclerView;
    GPSTracker gps;
    double latitude = 0, longitude = 0;
    List<Address> myList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventoFragment newInstance(int columnCount) {
        EventoFragment fragment = new EventoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evento_list, container, false);

        gps = new GPSTracker(getActivity());

        if(gps.isCanGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            System.out.println("Latitud: "+latitude + "longitud: "+longitude);
            Geocoder myLocation = new Geocoder(getContext());
            try {
                myList = myLocation.getFromLocation(37.4252347, -6.1679995, 1);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if(myList != null) {
                try {
                    System.out.println("ESTOY EN; "+myList.get(0).getCountryName());
                    System.out.println("ESTOY EN; "+myList.get(0).getLocality());
                    System.out.println("ESTOY EN; "+myList.get(0).getAddressLine(0));
                    System.out.println("ESTOY EN; "+myList.get(0).getAdminArea());
                    System.out.println("ESTOY EN; "+myList.get(0).getPremises());
                    System.out.println("ESTOY EN; "+myList.get(0).getExtras());
                    System.out.println("ESTOY EN; "+myList.get(0).getLocale());
                    System.out.println("ESTOY EN; "+myList.get(0).getThoroughfare());

                }catch (Exception e){}
            }

        }else{
            gps.showSettingsAlert();
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            getEventos();
        }
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ITectium) {
            mListener = (ITectium) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ITectium");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void getEventos() {

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service = retrofit1.create(IRetrofit.class);
        Call<Eventos> autocompleteList1 = service.getFullEvents();

        autocompleteList1.enqueue(new Callback<Eventos>() {
            @Override
            public void onResponse(Response<Eventos> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    Eventos result= response.body();


                    recyclerView.setAdapter(new MyEventoRecyclerViewAdapter(result.getData(), mListener));

                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }
}
