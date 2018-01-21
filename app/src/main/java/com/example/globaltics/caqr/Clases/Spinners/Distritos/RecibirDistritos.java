package com.example.globaltics.caqr.Clases.Spinners.Distritos;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
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
 * Created by Sam on 19/08/2017.
 */

public class RecibirDistritos extends AsyncTask<Void,Void,String> {
    private Context c;
    private String urla,id,accion3;
    private Spinner distrito;
    public RecibirDistritos(Context c, String urla, String id, Spinner distrito, String accion3) {
        this.c = c;
        this.urla = urla;
        this.id = id;
        this.distrito = distrito;
        this.accion3 = accion3;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null){
            Toast.makeText(c,"No tiene internet", Toast.LENGTH_SHORT).show();
        }else {
            new AnalizadorDistritos(c,distrito,s).execute();
        }
    }

    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueDistritos(accion3,id).packageData());
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
                    while ((linea = br.readLine())!=null){
                        respuesta.append(linea+"n");
                    }
                    br.close();
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
