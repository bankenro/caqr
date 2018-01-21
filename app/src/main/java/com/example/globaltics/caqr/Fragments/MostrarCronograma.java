package com.example.globaltics.caqr.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.globaltics.caqr.Clases.Views.MostrarEventos.DescargaCronogramas;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarCronograma extends Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {


    public MostrarCronograma() {
        // Required empty public constructor
    }
    private String di;
    private RecyclerView cronograma;
    private TextView dia1t,dia2t,dia3t,dia4t;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String di1 = "2017-10-03";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mostrar_cronograma, container, false);
        dia1t = view.findViewById(R.id.dia1);
        dia2t = view.findViewById(R.id.dia2);
        dia3t = view.findViewById(R.id.dia3);
        dia4t = view.findViewById(R.id.dia4);
        cronograma = view.findViewById(R.id.cronograma);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        dia1t.setOnClickListener(MostrarCronograma.this);
        dia2t.setOnClickListener(MostrarCronograma.this);
        dia3t.setOnClickListener(MostrarCronograma.this);
        dia4t.setOnClickListener(MostrarCronograma.this);
        swipeRefreshLayout.setOnRefreshListener(MostrarCronograma.this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                descargar(di1);
                cambiarcolor(dia1t);
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dia1:
                di = "2018-01-18";
                restaurarcolor();
                cambiarcolor(dia1t);
                descargar(di);
                break;
            case R.id.dia2:
                di = "2018-01-19";
                restaurarcolor();
                cambiarcolor(dia2t);
                descargar(di);
                break;
            case R.id.dia3:
                di = "2018-01-20";
                restaurarcolor();
                cambiarcolor(dia3t);
                descargar(di);
                break;
            case R.id.dia4:
                di = "2018-01-21";
                restaurarcolor();
                cambiarcolor(dia4t);
                descargar(di);
                break;
        }
    }

    private void cambiarcolor(TextView dia1tc) {
        dia1tc.setTextColor(ContextCompat.getColor(getActivity(), R.color.blanco));
        dia1tc.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.celeste));
    }

    private void descargar(String fecha){
        String accion = MD5.encrypt("mcro");
        swipeRefreshLayout.setRefreshing(true);
        new DescargaCronogramas(getActivity(),urla,accion,fecha,cronograma,swipeRefreshLayout).execute();
    }

    private void restaurarcolor() {
        dia1t.setTextColor(ContextCompat.getColor(getActivity(), R.color.negro));
        dia1t.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blanco));
        dia2t.setTextColor(ContextCompat.getColor(getActivity(), R.color.negro));
        dia2t.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blanco));
        dia3t.setTextColor(ContextCompat.getColor(getActivity(), R.color.negro));
        dia3t.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blanco));
        dia4t.setTextColor(ContextCompat.getColor(getActivity(), R.color.negro));
        dia4t.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.blanco));
    }

    @Override
    public void onRefresh() {
        descargar(di1);
    }
}