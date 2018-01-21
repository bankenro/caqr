package com.example.globaltics.caqr.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.globaltics.caqr.Clases.Views.MostrarPonentes.DescargarPonente;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarPonentes extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    public MostrarPonentes() {
        // Required empty public constructor
    }
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mostrar_ponentes, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rv = view.findViewById(R.id.rv);

        swipeRefreshLayout.setOnRefreshListener(MostrarPonentes.this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                descargar();
            }
        });
        return view;

    }

    @Override
    public void onRefresh() {
        descargar();
    }

    private void descargar() {
        swipeRefreshLayout.setRefreshing(true);
        String accion = MD5.encrypt("mpo");
        new DescargarPonente(getActivity(),urla,accion,rv,swipeRefreshLayout).execute();
    }
}
