package com.example.globaltics.caqr.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.globaltics.caqr.Clases.Views.MostrarInscrito.DescargarInscrito;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarInscripcion extends Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{


    public RegistrarInscripcion() {
        // Required empty public constructor
    }

    private EditText nombre,baucher;
    private Button inscr;
    private TextView nodata;
    //private ListView inscritos;
    private RecyclerView inscritos;
    private String ida,nombrea,tipo;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_inscripcion, container, false);
        nombre = view.findViewById(R.id.nombre);
        baucher = view.findViewById(R.id.baucher);
        inscr = view.findViewById(R.id.inscr);
        nodata = view.findViewById(R.id.nodata);
        inscritos = view.findViewById(R.id.inscritos);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);


        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        ida = preferences.getString("codigo","");
        nombrea = preferences.getString("nombre","");
        tipo = preferences.getString("a","");

        nombre.setText(nombrea);



        inscr.setOnClickListener(RegistrarInscripcion.this);
        swipeRefreshLayout.setOnRefreshListener(RegistrarInscripcion.this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mostrar();
            }
        });
        return  view;
    }

    private void mostrar() {
        String accion = MD5.encrypt("inscrito");
        swipeRefreshLayout.setRefreshing(true);
        DescargarInscrito mi = new DescargarInscrito(getActivity(),urla,accion,ida,inscritos,swipeRefreshLayout);
        mi.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.inscr:
                registrar();
                break;
        }
    }

    private void registrar() {
        String accion = MD5.encrypt("inscripcion");
//        Toast.makeText(getActivity(),ida+nombrea+accion,Toast.LENGTH_SHORT).show();
        com.example.globaltics.caqr.Clases.RegistrarInscripcion.RegistrarInscripcion re =
                new com.example.globaltics.caqr.Clases.RegistrarInscripcion.RegistrarInscripcion(getActivity(),urla,accion,ida,baucher.getText().toString());
        re.execute();
    }

    @Override
    public void onRefresh() {
        mostrar();
    }
}
