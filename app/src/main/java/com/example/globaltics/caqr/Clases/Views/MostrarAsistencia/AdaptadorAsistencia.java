package com.example.globaltics.caqr.Clases.Views.MostrarAsistencia;

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
import java.util.Objects;

/**
 * Created by GlobalTIC's on 9/09/2017.
 */

public class AdaptadorAsistencia extends RecyclerView.Adapter<CuerpoAsistencia> {
    private Context c;
    private ArrayList<Asistencia> asistencias;

    public AdaptadorAsistencia(Context c, ArrayList<Asistencia> asistencias) {
        this.c = c;
        this.asistencias = asistencias;
    }

    @Override
    public CuerpoAsistencia onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_asistencia, parent, false);
        CuerpoAsistencia cuerpoAsistencia = new CuerpoAsistencia(view);
        return cuerpoAsistencia;
    }

    @Override
    public void onBindViewHolder(CuerpoAsistencia holder, int position) {
        Asistencia asi = asistencias.get(position);
        holder.nombre.setText(asi.getNombre());
        holder.codigo.setText(asi.getEntrada());

        String imagen = asi.getFoto();
        byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.foto.setImageBitmap(bitmap);
        holder.activom.setText(asi.getSalida());

    }

    @Override
    public int getItemCount() {
        return asistencias.size();
    }
}
