package com.example.globaltics.caqr.Clases.Views.MostrarEventos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.globaltics.caqr.R;

import java.util.ArrayList;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class AdaptadorCronogramas extends RecyclerView.Adapter<CuerpoCronogramas>{
    private Context c;
    private ArrayList<Cronogramas>cronogramas;
    public AdaptadorCronogramas(Context c, ArrayList<Cronogramas> cronogramas) {
        this.c = c;
        this.cronogramas = cronogramas;
    }

    @Override
    public CuerpoCronogramas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_evento,parent,false);
        CuerpoCronogramas cuerpoCronogramas = new CuerpoCronogramas(view);
        return cuerpoCronogramas;
    }

    @Override
    public void onBindViewHolder(CuerpoCronogramas holder, int position) {
        Cronogramas cr = cronogramas.get(position);
        holder.fecha.setText(cr.getFecha());
        holder.hora.setText(cr.getHorai()+"-"+cr.getHoraf());
        holder.titulo.setText(cr.getTitulo());
        holder.ponente.setText(cr.getNombre());
    }

    @Override
    public int getItemCount() {
        return cronogramas.size();
    }
}
