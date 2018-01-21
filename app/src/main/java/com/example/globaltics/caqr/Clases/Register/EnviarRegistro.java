package com.example.globaltics.caqr.Clases.Register;

import android.app.ProgressDialog;
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
 * Created by SamGM on 14/04/2017.
 */

public class EnviarRegistro extends AsyncTask<Void,Void,String> {
    private Context c;
    private ProgressDialog pd;
    private TextView refresh;
    private String urla,nombre1,apellidop1,apellidom1,correo1,documento1,sexo1,codigo1,contraseña1,
            telefono1,fecha1,foto1,tdocumento1,lnacimiento;
    public EnviarRegistro(Context c, String urla , String nombre1, String apellidop1, String apellidom1, String correo1,
                          String documento1, String sexo1, String codigo1, String contraseña1,
                          String telefono1, String fecha1, String foto1, String tdocumento1, TextView refresh,
                          String lnacimiento) {
        this.c = c;
        this.urla = urla;
        this.nombre1 = nombre1;
        this.apellidop1 = apellidop1;
        this.apellidom1 = apellidom1;
        this.correo1 = correo1;
        this.documento1 = documento1;
        this.sexo1 = sexo1;
        this.foto1 = foto1;
        this.codigo1 = codigo1;
        this.contraseña1 = contraseña1;
        this.telefono1 = telefono1;
        this.fecha1 = fecha1;
        this.tdocumento1 = tdocumento1;
        this.refresh = refresh;
        this.lnacimiento = lnacimiento;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return this.registrar();
    }

    private String registrar() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if(con==null){
            return null;
        }
        try{
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueRegistro(nombre1,apellidop1,apellidom1,correo1,documento1,sexo1,foto1,codigo1,contraseña1,
                    telefono1,fecha1,tdocumento1,lnacimiento).packageData());
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Registrando");
        pd.setMessage("Por favor espere un momento");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if (s==null){
            Toast.makeText(c,"No tiene internet", Toast.LENGTH_SHORT).show();
        }else{
            AnalizadorRegistro an = new AnalizadorRegistro(c,s,codigo1,contraseña1,refresh);
            an.execute();
        }
    }
}
