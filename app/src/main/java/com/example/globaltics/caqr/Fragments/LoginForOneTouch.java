package com.example.globaltics.caqr.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.globaltics.caqr.Datos.CustomAdapterUsuario;
import com.example.globaltics.caqr.Datos.UsuariosSqlite;
import com.example.globaltics.caqr.Datos.UsuariosUsuarios;
import com.example.globaltics.caqr.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginForOneTouch extends Fragment implements View.OnClickListener{

    public LoginForOneTouch() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_for_one_touch, container, false);
        ListView usuarios = (ListView)view.findViewById(R.id.usuarios);
        Button ingresar = (Button)view.findViewById(R.id.ingresaro);
        Button registrar = (Button)view.findViewById(R.id.registraro);


        UsuariosSqlite usuariosSqlite = new UsuariosSqlite(getActivity(),"UsersDB.sqlite",null,1);
        ArrayList<UsuariosUsuarios> usuarioslist = new ArrayList<>();
        usuarioslist.clear();
        CustomAdapterUsuario adapterUsuario = new CustomAdapterUsuario(getActivity(),R.layout.usuarios,usuarioslist);

        Cursor cursor = usuariosSqlite.getData("SELECT * FROM USERS");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String codigo = cursor.getString(2);
            String imagen = cursor.getString(3);
            String tipo = cursor.getString(4);
            usuarioslist.add(new UsuariosUsuarios(id,nombre,codigo,imagen,tipo));
        }
        adapterUsuario.notifyDataSetChanged();

        ingresar.setOnClickListener(LoginForOneTouch.this);
        registrar.setOnClickListener(LoginForOneTouch.this);

        usuarios.setAdapter(adapterUsuario);

        return view;
    }

    @Override
    public void onClick(View view) {
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        switch (view.getId()){
            case R.id.ingresaro:
                fragment = new LoginFragment();
                fragmentTransaction = true;
                break;
            case R.id.registraro:
                fragment = new RegisterFragment();
                fragmentTransaction = true;
                break;
        }
        if(fragmentTransaction){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fragment).addToBackStack(null).commit();
        }
    }
}

