package com.example.globaltics.caqr.Clases.RegistrarPonentes;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class AnalizarPonentes extends AsyncTask<Void,Void,Integer>{
    private Context c;
    private  String s,mensaje;
    private Dialog d3;
    public AnalizarPonentes(Context c, String s, Dialog d3) {
        this.c = c;
        this.s = s;
        this.d3 = d3;
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer==0){
            Toast.makeText(c,"No se pudo analizar",Toast.LENGTH_SHORT).show();
        }else {
            if (Objects.equals(mensaje,"Ponencia Registrada")){
                d3.dismiss();
            }
            Toast.makeText(c,mensaje,Toast.LENGTH_SHORT).show();
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
