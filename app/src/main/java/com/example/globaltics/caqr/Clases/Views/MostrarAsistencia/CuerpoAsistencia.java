package com.example.globaltics.caqr.Clases.Views.MostrarAsistencia;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.globaltics.caqr.Clases.Views.ItemClickListener;
import com.example.globaltics.caqr.R;

/**
 * Created by GlobalTIC's on 9/09/2017.
 */

public class CuerpoAsistencia extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ItemClickListener itemClickListener;
    TextView nombre,codigo,activom;
    ImageView foto;
    public CuerpoAsistencia(View itemView) {
        super(itemView);
        nombre = (TextView)itemView.findViewById(R.id.nombre);
        codigo = (TextView)itemView.findViewById(R.id.codigo);
        foto = (ImageView)itemView.findViewById(R.id.foto);
        activom = (TextView)itemView.findViewById(R.id.activo);
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
