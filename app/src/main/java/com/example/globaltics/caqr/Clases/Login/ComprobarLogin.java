package com.example.globaltics.caqr.Clases.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.globaltics.caqr.Clases.Conexion;
import com.example.globaltics.caqr.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.concurrent.Delayed;

/**
 * Created by SamGM on 13/04/2017.
 */

public class ComprobarLogin extends AsyncTask<Void,Void,String> {
    private Context c;
    private ProgressDialog pd;
    private String usuario,contraseña,urla;
    private TextView refresh;
    public ComprobarLogin(Context c, String urla, String usuario, String password, TextView refresh) {
        this.c = c;
        this.urla = urla;
        this.usuario = usuario;
        this.contraseña = password;
        this.refresh = refresh;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c,android.R.style.Theme_Translucent_NoTitleBar);
        pd.show();
        pd.setContentView(R.layout.login);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        if(s==null){
            Toast.makeText(c,"No tiene internet", Toast.LENGTH_SHORT).show();
            refresh.setVisibility(View.VISIBLE);
        }else {
            AnalizadorLogin a= new AnalizadorLogin(c,s);
            a.execute();
        }

    }

    @Override
    protected String doInBackground(Void... params) {
        return this.iniciarsesion();
    }

    private String iniciarsesion() {
        HttpURLConnection con = Conexion.httpURLConnection(urla);
        if(con==null){
            return null;
        }
        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(new EmpaqueLogin(usuario,contraseña).packageData());

            bw.flush();
            bw.close();
            os.close();
            int responseCode = con.getResponseCode();
            if(responseCode==con.HTTP_OK){
                InputStream is = con.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                if(br!=null){
                    while ((line=br.readLine())!=null){
                        response.append(line+"n");
                    }
                }else{
                    return  null;
                }
                return response.toString();
            }else{
                return String.valueOf(responseCode);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
