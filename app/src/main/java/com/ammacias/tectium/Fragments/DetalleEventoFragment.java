package com.ammacias.tectium.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import android.app.AlertDialog;

import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Clases.Evento_categoria;
import com.ammacias.tectium.Clases.Eventos_categorias;
import com.ammacias.tectium.Clases.Example;
import com.ammacias.tectium.Clases.Usuario;
import com.ammacias.tectium.Interfaces.IRetrofit;
import com.ammacias.tectium.MainActivity;
import com.ammacias.tectium.R;
import com.ammacias.tectium.Recycler.OwnEventFragment;
import com.ammacias.tectium.TabsActivity;
import com.ammacias.tectium.Utils.Application_vars;
import com.ammacias.tectium.Utils.RoundedCornersTransform;
import com.ammacias.tectium.localdb.CategoriaDB;
import com.ammacias.tectium.localdb.CategoriaDBDao;
import com.ammacias.tectium.localdb.DatabaseConnection;
import com.robertlevonyan.views.chip.Chip;
import com.robertlevonyan.views.chip.OnChipClickListener;
import com.robertlevonyan.views.chip.OnCloseClickListener;
import com.robertlevonyan.views.chip.OnIconClickListener;
import com.robertlevonyan.views.chip.OnSelectClickListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleEventoFragment extends Fragment {

    private TagContainerLayout mTagContainerLayout1;

    List<String> tags_;
    List<String> spinnerArray;
    Spinner text_tag;
    Evento evento;
    List<String> list2;


    //Edit
    EditText nombre, sitio, descripcion,fecha, precio;
    Button btn_edit;
    ImageView fotoEvento;
    public DetalleEventoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalle_evento, container, false);

        Bundle extras = getArguments();
        evento = (Evento) Parcels.unwrap(extras.getParcelable("evento"));

        nombre = (EditText)v.findViewById(R.id.nombre);
        sitio = (EditText)v.findViewById(R.id.sitio);
        descripcion = (EditText)v.findViewById(R.id.descripcion);
        fecha = (EditText)v.findViewById(R.id.fecha);
        precio = (EditText)v.findViewById(R.id.precio);
        btn_edit = (Button)v.findViewById(R.id.btn_edit);
        fotoEvento = (ImageView)v.findViewById(R.id.fotoEvento);
        //Pinto Picasso
        Picasso.with(getActivity())
                .load(R.drawable.imagenevento)
                .resize(50, 50)
                .transform(new RoundedCornersTransform())
                .into(fotoEvento);


        nombre.setText(evento.getNombre());
        sitio.setText(evento.getSitio());
        descripcion.setText(evento.getDescripcion());
        fecha.setText(evento.getFecha());
        precio.setText(evento.getPrecio());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarOwnEvent();
            }
        });


        text_tag = (Spinner)v.findViewById(R.id.text_tag);

        list2 = new ArrayList<String>();

        datosSpinner();



        mTagContainerLayout1 = (TagContainerLayout) v.findViewById(R.id.tagcontainerLayout1);

        // Set custom click listener
        mTagContainerLayout1.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                /*Toast.makeText(getActivity(), "click-position:" + position + ", text:" + text,
                        Toast.LENGTH_SHORT).show();*/
            }

            //Cuando clickeo en un tag, creamos un dialogo de confirmacion para borrar el tag.
            //Si confirma, eliminamos el tag del recycler, lo añadimos al array del spinner, y
            //llamamos a una peticion para q borre el tag de la tabla intermedia.
            @Override
            public void onTagLongClick(final int position, final String text) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Eliminar TAG")
                        .setMessage("Vas a eliminar el tag "+text)
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTagFromEvent(text);
                                if (spinnerArray.size()==0) {
                                    text_tag.setEnabled(true);
                                }
                                spinnerArray.add(text);
                                System.out.println(spinnerArray.size());
                                mTagContainerLayout1.removeTag(position);
                                text_tag.setSelection(0);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }

            @Override
            public void onTagCrossClick(int position) {
//                mTagContainerLayout1.removeTag(position);
                /*Toast.makeText(getActivity(), "Click TagView cross! position = " + position,
                        Toast.LENGTH_SHORT).show();*/
            }
        });


        //mTagContainerLayout1.setTags(list2);


        //Añadimos el TAG al recycler de la vista. Lo quitamos de la lista del spinner y lo
        // añadimos a la la tabla intermedia por retrofit

        Button btnAddTag = (Button) v.findViewById(R.id.btn_add_tag);
        btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerArray.size()!=0){
                    mTagContainerLayout1.addTag(text_tag.getSelectedItem().toString());

                    addTagToEvent(text_tag.getSelectedItem().toString());

                    spinnerArray.remove(text_tag.getSelectedItem().toString());

                    text_tag.setSelection(0);
                }else{
                    text_tag.setEnabled(false);
                }

                // Add tag in the specified position
//                mTagContainerLayout1.addTag(text.getText().toString(), 4);
            }
        });
        return v;
    }

    //Petición retrofit que elimina el tag del evento
    private void deleteTagFromEvent(String text) {
        CategoriaDBDao categoriaDBDao = DatabaseConnection.getCategoriaDBDao(getActivity());
        List<CategoriaDB> categoriaDB = categoriaDBDao.loadAll();
        String id_tag = "";

        for (CategoriaDB c :categoriaDB) {
            if (c.getNombre().equalsIgnoreCase(text)){
                id_tag = String.valueOf(c.getId());
            }
        }

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        System.out.println("El tag a eliminar es: "+id_tag +" en "+evento.getId());
        retrofit1.create(IRetrofit.class).eliminarEventoCategoria(id_tag, evento.getId()).enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        System.out.println("Tag eliminado");

                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al eliminar tag: "+t.getMessage());
            }
        });
    }

    //Petición retrofit que añade el tag al evento
    private void addTagToEvent(String taG_name) {
        CategoriaDBDao categoriaDBDao = DatabaseConnection.getCategoriaDBDao(getActivity());
        List<CategoriaDB> categoriaDB = categoriaDBDao.loadAll();
        String id_tag = "";

        for (CategoriaDB c :categoriaDB) {
            if (c.getNombre().equalsIgnoreCase(taG_name)){
                id_tag = String.valueOf(c.getId());
            }
        }


        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        System.out.println("El tag a añadir es: "+id_tag +" en "+evento.getId());
        retrofit1.create(IRetrofit.class).crearEventoCategoria(id_tag, evento.getId()).enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        System.out.println("Tag editado");

                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al crear el tag: "+t.getMessage());
            }
        });
    }

    //Petición retrofit que que edita un evento creado por el usuario
    private void editarOwnEvent() {
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if (precio.getText().toString().equals("0")){
            precio.setText("Gratis");
        }

        retrofit1.create(IRetrofit.class).editOwnEvent(nombre.getText().toString(),sitio.getText().toString(),
                descripcion.getText().toString(), fecha.getText().toString(),precio.getText().toString(), evento.getId()).enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Response<Example> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        System.out.println("Evento editado");
                        Fragment f = new OwnEventFragment();

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content, f)
                                .commit();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al dar el usuario: "+t.getMessage());
            }
        });
    }

    //Petición retrofit que trae las categorias de un evento para darselas al spinner
    private void datosSpinner() {

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(IRetrofit.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit1.create(IRetrofit.class).getCatFromEvent(evento.getId()).enqueue(new Callback<Eventos_categorias>() {

            @Override
            public void onResponse(Response<Eventos_categorias> response, Retrofit retrofit) {

                if (response.isSuccess()){
                 if (response.body().getStatus().equalsIgnoreCase("success")){
                     Eventos_categorias eventos_cat = response.body();
                     CategoriaDBDao categoriaDBDao = DatabaseConnection.getCategoriaDBDao(getActivity());
                     List<CategoriaDB> categoriaDB = categoriaDBDao.loadAll();

                     spinnerArray = new ArrayList<String>();

                     System.out.println("Size: "+response.body().getData().size());
                     for (Evento_categoria ec:eventos_cat.getData()) {
                         System.out.println("Las cat son: "+ec.getIdCategoria());
                     }
                     //Para cada categoria
                     for (CategoriaDB c:categoriaDB) {
                         //Para cada evento_categoria
                         System.out.println("Para: "+c.getNombre());
                         for (Evento_categoria ec:eventos_cat.getData()) {
                             //Si el id de la categoria COINCIDE con el id del evento_categoria
                             System.out.println(c.getId() + " con " + Integer.parseInt(ec.getIdCategoria()));
                             if (c.getId()==(Integer.parseInt(ec.getIdCategoria()))){
                                 //Añado a la lista de categorias
                                 System.out.println("Añado a list2");
                                 list2.add(c.getNombre());
                             }
                         }
                         //Si la lista de categorias no tiene la categoria, añado al spinner
                         System.out.println("Si no contiene : "+c.getNombre());
                         if (!list2.contains(c.getNombre())){

                             System.out.println("Añado a spinner");
                             spinnerArray.add(c.getNombre());
                         }
                     }

                     System.out.println("Doy las cosas");
                     mTagContainerLayout1.setTags(list2);


                     ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);
                     spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                     text_tag.setAdapter(spinnerArrayAdapter);
                 }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("Error al crear el usuario: "+t.getMessage());
            }
        });
    }


    public class TagRecyclerViewAdapter
            extends RecyclerView.Adapter<TagRecyclerViewAdapter.TagViewHolder>{

        private Context mContext;
        private String[] mData;
        private View.OnClickListener mOnClickListener;

        public TagRecyclerViewAdapter(Context context, String[] data){
            this.mContext = context;
            this.mData = data;
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TagViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.view_recyclerview_item, parent, false), mOnClickListener);
        }

        @Override
        public void onBindViewHolder(TagViewHolder holder, int position) {
            holder.tagContainerLayout.setTags(mData);
            holder.button.setOnClickListener(mOnClickListener);
        }

        public void setOnClickListener(View.OnClickListener listener){
            this.mOnClickListener = listener;
        }

        class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TagContainerLayout tagContainerLayout;
            View.OnClickListener clickListener;
            Button button;

            public TagViewHolder(View v, View.OnClickListener listener){
                super(v);
                this.clickListener = listener;
                tagContainerLayout = (TagContainerLayout) v.findViewById(R.id.tagcontainerLayout);
                button = (Button) v.findViewById(R.id.button);
//                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (clickListener != null){
                    clickListener.onClick(v);
                }
            }
        }
    }
}
