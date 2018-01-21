package com.example.globaltics.caqr.Clases.Views.MostrarPonentes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.globaltics.caqr.Clases.Views.ItemClickListener;
import com.example.globaltics.caqr.R;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class CuerpoPonente extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ItemClickListener itemClickListener;
    ImageView foto;
    TextView nombre,pais,introduccion;
    public CuerpoPonente(View itemView) {
        super(itemView);
        foto = itemView.findViewById(R.id.foto);
        nombre = itemView.findViewById(R.id.nombre);
        pais = itemView.findViewById(R.id.pais);
        introduccion = itemView.findViewById(R.id.introduccion);
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
