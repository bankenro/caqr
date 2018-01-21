package com.example.globaltics.caqr.Clases.Views.MostrarInscrito;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.Conexion;
import com.example.globaltics.caqr.Clases.Views.MostrarInscrito.AnalizadorInscritos;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by GlobalTIC's on 9/09/2017.
 */

public class DescargarInscrito extends AsyncTask<Void,Integer,String> {
    private Context c;
    private String urla,accion,ida;
    private RecyclerView inscritos;
    private SwipeRefreshLayout swipeRefreshLayout;
    public DescargarInscrito(Context c, String urla, String accion, String ida, RecyclerView inscritos, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.ida = ida;
        this.inscritos = inscritos;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s != null) {
            if (!s.contains("[]")){
                new AnalizadorInscritos(c,s,inscritos,accion,swipeRefreshLayout).execute();
            }else {
                swipeRefreshLayout.setRefreshing(false);
            }
        }else {
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.mostrar();
    }

    private String mostrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueInscrito(accion,ida).packageData());
            bw.flush();
            bw.close();
            os.close();
            int resp = con.getResponseCode();
            if (resp==con.HTTP_OK){
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuffer respuesta = new StringBuffer();
                if (br!=null){
                    while ((linea=br.readLine())!=null){
                        respuesta.append(linea+"n");
                    }
                }else {
                    return null;
                }
                return respuesta.toString();
            }else {
                return String.valueOf(resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

