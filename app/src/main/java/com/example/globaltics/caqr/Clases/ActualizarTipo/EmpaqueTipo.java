package com.example.globaltics.caqr.Clases.ActualizarTipo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by GlobalTIC's on 9/09/2017.
 */

public class EmpaqueTipo {
    private String accion,id,activoid;
    public EmpaqueTipo(String accion, String id, String activoid) {
        this.accion = accion;
        this.id = id;
        this.activoid = activoid;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("1",id);
            jo.put("2",activoid);
            Boolean primero = true;
            Iterator i = jo.keys();
            do {
                String key = i.next().toString();
                String value = jo.get(key).toString();
                if (primero){
                    primero = false;
                }else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));
            }while (i.hasNext());
            return sb.toString();
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
