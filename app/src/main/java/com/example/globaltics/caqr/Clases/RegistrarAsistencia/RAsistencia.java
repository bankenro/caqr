package com.example.globaltics.caqr.Clases.RegistrarAsistencia;

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

public class RAsistencia extends AsyncTask<Void,Void,String> {
    private Context c;
    private String urla,accion,mensaje0;
    private TextView barcodeInfo;
    public RAsistencia(Context c, String urla, String accion, String mensaje0, TextView barcodeInfo) {
        this.c = c;
        this.urla = urla;
        this.accion = accion;
        this.mensaje0 = mensaje0;
        this.barcodeInfo = barcodeInfo;
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
            AnalizarRAsistencia an = new AnalizarRAsistencia(c,s,barcodeInfo);
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
            bw.write(new EmpaqueRAsistencia(accion,mensaje0).packageData());
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
