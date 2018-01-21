package com.example.globaltics.caqr.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.globaltics.caqr.Clases.Views.MostrarEventos.DescargaCronogramas;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarTalleres extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    public MostrarTalleres() {
        // Required empty public constructor
    }

    private RecyclerView cronograma;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mostrar_talleres, container, false);
        cronograma = view.findViewById(R.id.cronograma);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                descargar("");
            }
        });
        return view;
    }
    private void descargar(String fecha){
        String accion = MD5.encrypt("mtall");
        swipeRefreshLayout.setRefreshing(true);
        new DescargaCronogramas(getActivity(),urla,accion,fecha,cronograma,swipeRefreshLayout).execute();
    }

    @Override
    public void onRefresh() {
        descargar("");
    }
}
