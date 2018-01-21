package com.example.globaltics.caqr.Clases.Views.MostrarEventos;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.Conexion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class DescargaCronogramas extends AsyncTask<Void,Integer,String> {
    private Context c;
    private String urla,accion,dia;
    private RecyclerView cronograma;
    private SwipeRefreshLayout swipeRefreshLayout;
    public DescargaCronogramas(Context c, String urla, String accion, String dia, RecyclerView cronograma, SwipeRefreshLayout swipeRefreshLayout) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.dia = dia;
        this.cronograma = cronograma;
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.descargar();
    }

    private String descargar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueCronogramas(accion,dia).packageData());
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
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null ){
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizadorCronogramas(c,s,cronograma,swipeRefreshLayout).execute();
        }
    }
}
