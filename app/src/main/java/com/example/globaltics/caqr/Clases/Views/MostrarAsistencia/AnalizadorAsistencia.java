package com.example.globaltics.caqr.Clases.Views.MostrarAsistencia;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GlobalTIC's on 9/09/2017.
 */

public class AnalizadorAsistencia extends AsyncTask<Void,Integer,Integer> {
    private Context c;
    private String s;
    private RecyclerView asistencia;
    private ArrayList<Asistencia> asistencias = new ArrayList<>();
    public AnalizadorAsistencia(Context c, String s, RecyclerView asistencia) {
        this.c = c;
        this.s = s;
        this.asistencia = asistencia;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            Asistencia asistencia1;
            for (int i = 0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String nombre = jo.getString("nombre");
                String foto = jo.getString("foto");
                String entrada = jo.getString("entrada");
                String salida = jo.getString("salida");

                asistencia1 = new Asistencia();
                asistencia1.setNombre(nombre);
                asistencia1.setFoto(foto);
                asistencia1.setEntrada(entrada);
                asistencia1.setSalida(salida);
                asistencias.add(asistencia1);
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
        if (integer==0){
            Toast.makeText(c,"No se cargo la asistencia",Toast.LENGTH_SHORT).show();
        }else {
            AdaptadorAsistencia a = new AdaptadorAsistencia(c,asistencias);
            asistencia.setAdapter(a);
            asistencia.setLayoutManager(new LinearLayoutManager(c));
            //Helper.getListViewSize(asistencia);
        }

    }
}
