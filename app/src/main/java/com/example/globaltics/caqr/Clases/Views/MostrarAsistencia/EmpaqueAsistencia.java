package com.example.globaltics.caqr.Clases.Views.MostrarAsistencia;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by GlobalTIC's on 9/09/2017.
 */

public class EmpaqueAsistencia {
    private String accion,codigo,fecha1;
    public EmpaqueAsistencia(String accion, String codigo, String fecha1) {
        this.accion = accion;
        this.codigo = codigo;
        this.fecha1 = fecha1;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("1",codigo);
            jo.put("2",fecha1);
            Iterator i = jo.keys();
            Boolean primer = true;
            do {
                String key = i.next().toString();
                String value = jo.get(key).toString();
                if (primer){
                    primer = false;
                }else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));
            }while (i.hasNext());
            return sb.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
