package com.ammacias.tectium.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ammacias.tectium.Clases.Evento;
import com.ammacias.tectium.Interfaces.ITectium;
import com.ammacias.tectium.R;
import com.ammacias.tectium.Utils.RoundedCornersTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Evento} and makes a call to the
 * specified {@link com.ammacias.tectium.Interfaces.ITectium}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyOwnEventRecyclerViewAdapter extends RecyclerView.Adapter<MyOwnEventRecyclerViewAdapter.ViewHolder> {

    private final List<Evento> mValues;
    private final ITectium mListener;
    Context ctx;

    public MyOwnEventRecyclerViewAdapter(Context ctx,List<Evento> items, ITectium listener) {
        this.ctx= ctx;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ownevent_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.nombre.setText(mValues.get(position).getNombre());
        holder.sitio.setText(mValues.get(position).getSitio());
        holder.fecha.setText(mValues.get(position).getFecha());
        if (mValues.get(position).getPrecio().equals("Gratis")){
            holder.precio.setText(mValues.get(position).getPrecio());
        }else{
            holder.precio.setText(mValues.get(position).getPrecio()+ "€");
        }
        holder.descripcion.setText(mValues.get(position).getDescripcion());

        Picasso.with(ctx)
                .load(R.drawable.imagenevento)
                .resize(50, 50)
                .transform(new RoundedCornersTransform())
                .into(holder.foto);

        holder.compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickShare(holder.mItem);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClickOwnEvento(holder.mItem);
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
        public final ImageView compartir;/*
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
            compartir = (ImageView) view.findViewById(R.id.compartir);/*
            img_fecha = (ImageView) view.findViewById(R.id.img_fecha);
            img_precio = (ImageView) view.findViewById(R.id.img_precio);*/
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
