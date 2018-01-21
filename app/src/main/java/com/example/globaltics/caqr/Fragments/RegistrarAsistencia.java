package com.example.globaltics.caqr.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.globaltics.caqr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarAsistencia extends Fragment implements View.OnClickListener{


    public RegistrarAsistencia() {
        // Required empty public constructor
    }
    private Button regsal,reging;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_asistencia, container, false);
        reging = view.findViewById(R.id.reging);
        regsal = view.findViewById(R.id.regsal);
        reging.setOnClickListener(RegistrarAsistencia.this);
        regsal.setOnClickListener(RegistrarAsistencia.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.reging:
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new RegistrarIngreso();
                fragmentTransaction.replace(R.id.contenedorn,fragment);
                fragmentTransaction.addToBackStack(null).commit();
                break;
            case R.id.regsal:
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new RegistrarSalida();
                fragmentTransaction.replace(R.id.contenedorn,fragment);
                fragmentTransaction.addToBackStack(null).commit();
                break;
        }
    }
}
