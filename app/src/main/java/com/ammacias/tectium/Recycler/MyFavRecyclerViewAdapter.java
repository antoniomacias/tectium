package com.ammacias.tectium.Recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Interfaces.ITectium;
import com.ammacias.tectium.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Evento} and makes a call to the
 * specified {@link ITectium}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFavRecyclerViewAdapter extends RecyclerView.Adapter<MyFavRecyclerViewAdapter.ViewHolder> {

    private final List<Evento> mValues;
    private final ITectium mListener;

    public MyFavRecyclerViewAdapter(List<Evento> items, ITectium listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_fav_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position); holder.nombre.setText(mValues.get(position).getNombre());
        holder.sitio.setText(mValues.get(position).getSitio());
        holder.fecha.setText(mValues.get(position).getFecha());
        holder.precio.setText(mValues.get(position).getPrecio());
        holder.descripcion.setText(mValues.get(position).getDescripcion());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClickFavoritoRecycler(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombre;
        public final TextView sitio;
        public final TextView fecha;
        public final TextView precio;
        public final TextView descripcion;
        public final ImageView foto;
        public final ImageView fav;
        public final ImageView compartir;/**
         public final ImageView img_fecha;
         public final ImageView img_precio;*/
        public Evento mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombre = (TextView) view.findViewById(R.id.nombre);
            sitio = (TextView) view.findViewById(R.id.sitio);
            fecha = (TextView) view.findViewById(R.id.fecha);
            precio = (TextView) view.findViewById(R.id.precio);
            descripcion = (TextView) view.findViewById(R.id.descripcion);

            foto = (ImageView) view.findViewById(R.id.fotoEvento);
            fav = (ImageView) view.findViewById(R.id.fav);
            compartir = (ImageView) view.findViewById(R.id.compartir);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
