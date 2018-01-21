package com.example.globaltics.caqr.Clases.Views.MostrarEventos;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class AnalizadorCronogramas extends AsyncTask<Void,Integer,Integer> {
    private Context c;
    private String s;
    private RecyclerView cronograma;
    private ArrayList<Cronogramas> cronogramas = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    public AnalizadorCronogramas(Context c, String s, RecyclerView cronograma, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.s = s;
        this.cronograma = cronograma;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            Cronogramas cronogramas1;
            for (int i = 0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String id = jo.getString("id");
                String fecha = jo.getString("fecha");
                String horai = jo.getString("horai");
                String horaf = jo.getString("horaf");
                String titulo = jo.getString("titulo");
                String nombre = jo.getString("nombre");

                cronogramas1 = new Cronogramas();
                cronogramas1.setId(id);
                cronogramas1.setFecha(fecha);
                cronogramas1.setHorai(horai);
                cronogramas1.setHoraf(horaf);
                cronogramas1.setTitulo(titulo);
                cronogramas1.setNombre(nombre);
                cronogramas.add(cronogramas1);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        swipeRefreshLayout.setRefreshing(false);
        if (integer==0){
            Toast.makeText(c,"No se cargo el cronograma",Toast.LENGTH_SHORT).show();
        }else {
            AdaptadorCronogramas a = new AdaptadorCronogramas(c,cronogramas);
            cronograma.setAdapter(a);
            cronograma.setLayoutManager(new LinearLayoutManager(c));
            //Helper.getListViewSize(asistencia);
        }

    }
}
