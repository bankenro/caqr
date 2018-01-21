package com.example.globaltics.caqr.Clases.Register;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.Login.ComprobarLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;


/**
 * Created by SamGM on 14/04/2017.
 */

class AnalizadorRegistro extends AsyncTask<Void,Void,Integer> {
    private Context c;
    private String s,codigo1,contraseña1;
    private TextView refresh;
    private String mensaje;
    AnalizadorRegistro(Context c, String s, String codigo1, String contraseña1, TextView refresh) {
        this.s = s;
        this.c=c;
        this.codigo1 = codigo1;
        this.contraseña1 = contraseña1;
        this.refresh = refresh;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1){
            //String urla = "http://nationfis.hol.es/nonfc/login.php";
                Toast.makeText(c,mensaje, Toast.LENGTH_SHORT).show();

                new ComprobarLogin(c,urla,codigo1,contraseña1,refresh).execute();

        }else {
            Toast.makeText(c,"No registrado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }

    private Integer analizar() {
        try {
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            for (int i=0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                mensaje = jo.getString("mensaje");
            }
            return 1;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }
}
