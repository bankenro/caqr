package com.example.globaltics.caqr.Clases.Views.MostrarPonentes;

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

public class AnalizadorPonente extends AsyncTask<Void,Integer,Integer> {
    private Context c;
    private String s;
    private RecyclerView rv;
    private ArrayList<Ponente> ponente = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    public AnalizadorPonente(Context c, String s, RecyclerView rv, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.s = s;
        this.rv = rv;
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
            Ponente ponente1;
            for (int i = 0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String id = jo.getString("id");
                String texto = jo.getString("texto");
                String pais = jo.getString("pais");
                String nombre = jo.getString("nombre");
                String foto = jo.getString("foto");

                ponente1 = new Ponente();
                ponente1.setId(id);
                ponente1.setTexto(texto);
                ponente1.setPais(pais);
                ponente1.setNombre(nombre);
                ponente1.setFoto(foto);
                ponente.add(ponente1);
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
            AdaptadorPonente a = new AdaptadorPonente(c,ponente);
            rv.setAdapter(a);
            rv.setLayoutManager(new LinearLayoutManager(c));
            //Helper.getListViewSize(asistencia);
        }

    }
}
