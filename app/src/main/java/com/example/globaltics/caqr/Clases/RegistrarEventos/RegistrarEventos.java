package com.example.globaltics.caqr.Clases.RegistrarEventos;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.Conexion;
import com.example.globaltics.caqr.Clases.RegistrarPonentes.AnalizarPonentes;
import com.example.globaltics.caqr.Clases.RegistrarPonentes.EmpaquePonentes;

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

public class RegistrarEventos extends AsyncTask<Void,Void,String> {
    private Context c;
    private String urla, accion, id, fecha, horai, horaf, titulo;
    private Dialog d4;

    public RegistrarEventos(Context c, String urla, String accion, String id, String fecha, String horai, String horaf, String titulo, Dialog d4) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.id = id;
        this.fecha = fecha;
        this.horai = horai;
        this.horaf = horaf;
        this.titulo = titulo;
        this.d4 = d4;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.registrar();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null) {
            Toast.makeText(c, "No tiene internet", Toast.LENGTH_SHORT).show();
        } else {
            AnalizarEventos an = new AnalizarEventos(c, s, d4);
            an.execute();
        }
    }

    private String registrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con == null) {
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueEventos(accion, id, fecha,horai,horaf,titulo).packageData());
            bw.flush();
            bw.close();
            os.close();
            int respuesta = con.getResponseCode();
            if (respuesta == con.HTTP_OK) {
                InputStream is = con.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String linea;
                StringBuffer respuestast = new StringBuffer();
                if (br != null) {
                    while ((linea = br.readLine()) != null) {
                        respuestast.append(linea + "n");
                    }
                } else {
                    return null;
                }
                return respuestast.toString();
            } else {
                return String.valueOf(respuesta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}