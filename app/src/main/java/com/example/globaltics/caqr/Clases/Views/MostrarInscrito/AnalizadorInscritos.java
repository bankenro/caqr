package com.example.globaltics.caqr.Clases.Views.MostrarInscrito;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class AnalizadorInscritos extends AsyncTask<Void,Integer,Integer> {
    private Context c;
    private String s,accion;
    private RecyclerView inscritos;
    ArrayList<Inscrito> inscrito = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    public AnalizadorInscritos(Context c, String s, RecyclerView inscritos, String accion, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.s = s;
        this.inscritos = inscritos;
        this.accion = accion;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        swipeRefreshLayout.setRefreshing(false);
        if (integer==1){
            AdaptadorInscrito adaptadorInscritos = new AdaptadorInscrito(c,inscrito,accion);
            inscritos.setAdapter(adaptadorInscritos);
            inscritos.setLayoutManager(new LinearLayoutManager(c));
            //Helper.getListViewSize(inscritos);
        }else {
            Toast.makeText(c,"No encotrados",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizador();
    }

    private Integer analizador() {
        try {
            Inscrito inscrit;
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            inscrito.clear();
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                String id = jo.getString("id");
                String id2 = jo.getString("codigo");
                String activo = jo.getString("activo");
                String nombres = jo.getString("nombres");
                String foto = jo.getString("foto");

                inscrit = new Inscrito();

                inscrit.setId(id);
                inscrit.setId2(id2);
                inscrit.setActivo(activo);
                inscrit.setNombres(nombres);
                inscrit.setFoto(foto);
                inscrito.add(inscrit);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}