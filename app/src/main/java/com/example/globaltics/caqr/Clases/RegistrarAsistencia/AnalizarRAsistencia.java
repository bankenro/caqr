package com.example.globaltics.caqr.Clases.RegistrarAsistencia;

import android.content.Context;
import android.content.res.ObbInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.Login.ComprobarLogin;
import com.example.globaltics.caqr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;


/**
 * Created by GlobalTIC's on 5/09/2017.
 */

public class AnalizarRAsistencia extends AsyncTask<Void,Void,Integer> {
    private Context c;
    private String s,mensaje;
    private TextView barcodeInfo;
    public AnalizarRAsistencia(Context c, String s, TextView barcodeInfo) {
        this.c = c;
        this.s = s;
        this.barcodeInfo = barcodeInfo;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizar();
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1){

            if (mensaje!=null){
                if (Objects.equals(mensaje,"Asistencia Registrada")){
                    Toast.makeText(c,mensaje, Toast.LENGTH_SHORT).show();
                    barcodeInfo.setText(mensaje);
                    barcodeInfo.setTextColor(ContextCompat.getColor(c, R.color.activo));
                }else if (Objects.equals(mensaje,"No registro su ingreso")){
                    Toast.makeText(c,mensaje, Toast.LENGTH_SHORT).show();
                    barcodeInfo.setText(mensaje);
                    barcodeInfo.setTextColor(ContextCompat.getColor(c, R.color.medio));
                }else if (Objects.equals(mensaje,"No activo su inscripcion")){
                    Toast.makeText(c,mensaje, Toast.LENGTH_SHORT).show();
                    barcodeInfo.setText(mensaje);
                    barcodeInfo.setTextColor(ContextCompat.getColor(c, R.color.medio));
                }else if (Objects.equals(mensaje,"Entrada ya registrada") || Objects.equals(mensaje,"Salida ya registrada")){
                    Toast.makeText(c,mensaje, Toast.LENGTH_SHORT).show();
                    barcodeInfo.setText(mensaje);
                    barcodeInfo.setTextColor(ContextCompat.getColor(c, R.color.activo));
                }
            }else {
                Toast.makeText(c,"ASISTENCIA NO REGISTRADA", Toast.LENGTH_SHORT).show();
                barcodeInfo.setText(R.string.anr);
                barcodeInfo.setTextColor(ContextCompat.getColor(c, R.color.inactivo));
            }
        }else {
            Toast.makeText(c,"ASISTENCIA NO REGISTRADA", Toast.LENGTH_SHORT).show();
            barcodeInfo.setText(R.string.anr);
            barcodeInfo.setTextColor(ContextCompat.getColor(c, R.color.inactivo));
        }
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
