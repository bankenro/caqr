package com.example.globaltics.caqr.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.globaltics.caqr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarInscritos extends Fragment {


    public MostrarInscritos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_inscritos, container, false);
    }

}
