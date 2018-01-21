package com.example.globaltics.caqr.Clases.ActualizarTipo;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.globaltics.caqr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by GlobalTIC's on 9/09/2017.
 */

public class AnalizarTipo extends AsyncTask<Void,Void,Integer> {
    private Context c;
    private String s,mensaje;
    private Dialog d2;
    public AnalizarTipo(Context c, String s, Dialog d2) {
        this.c = c;
        this.s = s;
        this.d2= d2;
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(mensaje,"Accion Realizada")){
                d2.dismiss();
            }
            Toast.makeText(c,mensaje,Toast.LENGTH_SHORT).show();
        }
    }
    private Integer analizar() {
        try {
            JSONObject jo;
            JSONArray ja = new JSONArray(s);
            for (int i = 0; i<ja.length();i++){
                jo=ja.getJSONObject(i);
                mensaje = jo.getString("mensaje");
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
