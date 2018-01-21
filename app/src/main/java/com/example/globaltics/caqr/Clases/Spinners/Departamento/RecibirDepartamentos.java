package com.example.globaltics.caqr.Clases.Spinners.Departamento;

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

public class RecibirDepartamentos extends AsyncTask<Void,Void,String> {
    private Context c;
    private String urla,accion1,accion2,accion3;
    private Spinner departamento,provincia,distrito;
    public RecibirDepartamentos(Context c, String urla, Spinner departamento, Spinner provincia, Spinner distrito, String accion1, String accion2, String accion3) {
        this.c = c;
        this.urla = urla;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.accion1 = accion1;
        this.accion2 = accion2;
        this.accion3 = accion3;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s == null){
            Toast.makeText(c,"No tiene internet", Toast.LENGTH_SHORT).show();
        }else {
            new AnalizadorDepartamentos(c,urla,accion2,accion3,departamento,provincia,distrito,s).execute();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.recibir();
    }

    private String recibir() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if (con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueDepartamentos(accion1).packageData());
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
