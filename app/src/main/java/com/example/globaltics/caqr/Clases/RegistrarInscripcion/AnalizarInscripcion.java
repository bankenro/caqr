package com.example.globaltics.caqr.Clases.RegistrarInscripcion;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.Login.ComprobarLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * Created by GlobalTIC's on 5/09/2017.
 */

public class AnalizarInscripcion extends AsyncTask<Void,Void,Integer>{
    private Context c;
    private String s,mensaje;
    public AnalizarInscripcion(Context c, String s) {
        this.c = c;
        this.s = s;

    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1){
            if (mensaje!=null){
                Toast.makeText(c,mensaje, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(c,"No registrado", Toast.LENGTH_SHORT).show();
            }
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
