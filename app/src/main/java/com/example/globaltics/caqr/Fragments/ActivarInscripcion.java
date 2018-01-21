package com.example.globaltics.caqr.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.globaltics.caqr.Clases.Views.MostrarInscrito.DescargarInscrito;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivarInscripcion extends Fragment implements SearchView.OnQueryTextListener,SwipeRefreshLayout.OnRefreshListener {


    public ActivarInscripcion() {
        // Required empty public constructor
    }
    private SearchView searchView;
    //private ListView inscritos;
    private RecyclerView inscritos;
    private String accion;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activar_inscripcion, container, false);
        searchView = view.findViewById(R.id.sv);
        inscritos = view.findViewById(R.id.inscritos);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);



        if (getArguments() != null){
            accion = MD5.encrypt(getArguments().getString("accion",""));
        }
        /*if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle("YourTitle");
        }*/
        searchView.setOnQueryTextListener(ActivarInscripcion.this);
        swipeRefreshLayout.setOnRefreshListener(ActivarInscripcion.this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                descargar("");
            }
        });
        return  view;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        descargar(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        descargar(s);
        return false;
    }
    private void descargar(String s) {
        inscritos.setAdapter(null);
        swipeRefreshLayout.setRefreshing(true);
        //String accion = MD5.encrypt("inscritos");
        //MostrarInscrito mi = new MostrarInscrito(getActivity(),urla,accion,s,inscritos,nodata);
        DescargarInscrito mi = new DescargarInscrito(getActivity(),urla,accion,s,inscritos,swipeRefreshLayout);
        mi.execute();
    }

    @Override
    public void onRefresh() {
        descargar("");
    }
}
