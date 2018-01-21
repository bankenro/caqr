package com.example.globaltics.caqr.Clases.RegistrarPonentes;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.Conexion;
import com.example.globaltics.caqr.Clases.RegistrarInscripcion.AnalizarInscripcion;
import com.example.globaltics.caqr.Clases.RegistrarInscripcion.EmpaqueInscripcion;
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
 * Created by GlobalTIC's on 10/09/2017.
 */

public class RegistrarPonentes extends AsyncTask<Void,Void,String> {
    private Context c;
    private String urla,accion,id,texto,pais;
    private Dialog d3;
    public RegistrarPonentes(Context c, String urla, String accion, String id, String texto, String pais, Dialog d3) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.id = id;
        this.texto = texto;
        this.pais = pais;
        this.d3 = d3;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.registrar();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(c,"No tiene internet", Toast.LENGTH_SHORT).show();
        }else{
            AnalizarPonentes an = new AnalizarPonentes(c,s,d3);
            an.execute();
        }
    }

    private String registrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if(con==null){
            return null;
        }
        try{
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaquePonentes(accion,id,texto,pais).packageData());
            bw.flush();
            bw.close();
            os.close();
            int respuesta = con.getResponseCode();
            if(respuesta==con.HTTP_OK){
                InputStream is =con.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuffer respuestast = new StringBuffer();
                if(br!=null){
                    while ((linea=br.readLine())!=null){
                        respuestast.append(linea+"n");
                    }
                }else {
                    return null;
                }
                return respuestast.toString();
            }else{
                return String.valueOf(respuesta);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}