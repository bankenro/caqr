package com.example.globaltics.caqr.Fragments;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.globaltics.caqr.Clases.Views.MostrarAsistencia.DescargarAsistencia;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostrarAsistencia extends Fragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{


    public MostrarAsistencia() {
        // Required empty public constructor
    }
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView asistencia;
    private String codigo;
    private String accion = MD5.encrypt("maa");;
    private Button enviar;
    private TextView fecha;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mostrar_asistencia, container, false);
        asistencia = view.findViewById(R.id.asistencia);
        fecha = (TextView)view.findViewById(R.id.fecha);
        enviar = (Button)view.findViewById(R.id.enviar);
        fecha.setText(sdf.format(calendar.getTime()));
        swipeRefreshLayout =(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);

        SharedPreferences preferences = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        codigo = preferences.getString("codigo","");

        fecha.setOnClickListener(MostrarAsistencia.this);
        enviar.setOnClickListener(MostrarAsistencia.this);
        swipeRefreshLayout.setOnRefreshListener(MostrarAsistencia.this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mostrar();
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enviar:
                mostrar();
                break;
            case R.id.fecha:
                date();
                break;
        }
    }

    private void mostrar() {
        String fecha1 = fecha.getText().toString();
        swipeRefreshLayout.setRefreshing(true);
        asistencia.setAdapter(null);
        new DescargarAsistencia(getActivity(),urla,accion,codigo,fecha1,swipeRefreshLayout,asistencia).execute();
    }

    @Override
    public void onRefresh() {
        asistencia.setAdapter(null);
        mostrar();
    }
    private void date() {
        new DatePickerDialog(getActivity(),d,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updatedate();
        }
    };
    private void updatedate() {
        fecha.setText(sdf.format(calendar.getTime()));
    }
}
