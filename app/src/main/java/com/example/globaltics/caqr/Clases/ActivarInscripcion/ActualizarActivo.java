package com.example.globaltics.caqr.Clases.ActivarInscripcion;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
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
 * Created by GlobalTIC's on 5/09/2017.
 */

public class ActualizarActivo extends AsyncTask<Void,Void,String> {
    private Context c;
    private String urla,accion,activoid,id,id2;
    private Dialog d;
    private TextView activom;
    public ActualizarActivo(Context c, String urla, String accion, String activoid, String id, String id2, Dialog d, TextView activom) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.activoid = activoid;
        this.id = id;
        this.id2 = id2;
        this.d = d;
        this.activom = activom;

    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            Toast.makeText(c,"No tiene internet",Toast.LENGTH_SHORT).show();
        }else {
            new AnalizarActivo(c,s,d,activom,activoid).execute();
        }
    }
    @Override
    protected String doInBackground(Void... voids) {
        return this.activar();
    }

    private String activar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueActivo(accion,id,id2,activoid).packageData());
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
