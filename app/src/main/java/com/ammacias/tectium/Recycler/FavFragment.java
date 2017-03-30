package com.ammacias.tectium.Recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Clases.Evento_usuario;
import com.ammacias.tectium.Clases.Eventos;
import com.ammacias.tectium.Clases.Eventos_usuarios;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.ammacias.tectium.Interfaces.ITectium;
import com.ammacias.tectium.R;
import com.ammacias.tectium.Utils.Application_vars;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link ITectium}
 * interface.
 */
public class FavFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ITectium mListener;
    RecyclerView recyclerView;
    List<Evento> listaEventosFinal;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FavFragment newInstance(int columnCount) {
        FavFragment fragment = new FavFragment();
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
        View view = inflater.inflate(R.layout.fragment_fav_list, container, false);

        listaEventosFinal = new ArrayList<>();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            getDatos();
        }
        return view;
    }

    private void getDatos() {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service = retrofit1.create(IRetrofit.class);
        Call<Eventos_usuarios> autocompleteList1 = service.getFavEvents(((Application_vars)getActivity().getApplication()).getUsuario().getId());

        autocompleteList1.enqueue(new Callback<Eventos_usuarios>() {
            @Override
            public void onResponse(Response<Eventos_usuarios> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    Eventos_usuarios result= response.body();

                    recyclerView.setAdapter(new MyFavRecyclerViewAdapter(result.getData(), mListener));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    private void getEvento(String idEvento) {

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit service = retrofit1.create(IRetrofit.class);
        Call<Evento> autocompleteList1 = service.getEvent(idEvento);

        autocompleteList1.enqueue(new Callback<Evento>() {
            @Override
            public void onResponse(Response<Evento> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    Evento result= response.body();
                    listaEventosFinal.add(result);
                    System.out.println("Size: "+listaEventosFinal.size());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });

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


}
