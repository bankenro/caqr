package com.example.globaltics.caqr.Clases.Views.MostrarPonentes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.globaltics.caqr.R;

import java.util.ArrayList;

/*
 * Created by GlobalTIC's on 10/09/2017.
 */

public class AdaptadorPonente extends RecyclerView.Adapter<CuerpoPonente>{
    private Context c;
    private ArrayList<Ponente>ponente;
    public AdaptadorPonente(Context c, ArrayList<Ponente> ponente) {
        this.c = c;
        this.ponente = ponente;
    }

    @Override
    public CuerpoPonente onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_ponentes,parent,false);
        CuerpoPonente cuerpoPonente = new CuerpoPonente(view);
        return cuerpoPonente;
    }

    @Override
    public void onBindViewHolder(CuerpoPonente holder, int position) {
        Ponente ponente1 = ponente.get(position);
        holder.pais.setText(ponente1.getPais());
        holder.nombre.setText(ponente1.getNombre());
        holder.introduccion.setText(ponente1.getTexto());

        String imagen = ponente1.getFoto();
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.foto.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return ponente.size();
    }
}
