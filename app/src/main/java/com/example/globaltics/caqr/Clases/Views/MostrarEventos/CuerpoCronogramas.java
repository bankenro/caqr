package com.example.globaltics.caqr.Clases.Views.MostrarEventos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.globaltics.caqr.Clases.Views.ItemClickListener;
import com.example.globaltics.caqr.R;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class CuerpoCronogramas extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ItemClickListener itemClickListener;
    TextView fecha,hora,titulo,ponente;
    public CuerpoCronogramas(View itemView) {
        super(itemView);
        fecha = (TextView)itemView.findViewById(R.id.fecha);
        hora = (TextView)itemView.findViewById(R.id.hora);
        titulo = (TextView)itemView.findViewById(R.id.titulo);
        ponente = (TextView)itemView.findViewById(R.id.ponente);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View view) {
        //this.itemClickListener.onItemClick(getLayoutPosition());
    }
}
