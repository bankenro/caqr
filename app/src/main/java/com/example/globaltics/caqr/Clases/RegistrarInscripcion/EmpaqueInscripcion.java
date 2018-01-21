package com.example.globaltics.caqr.Clases.RegistrarInscripcion;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by GlobalTIC's on 5/09/2017.
 */

public class EmpaqueInscripcion {
    private String accion,ida,baucher;
    public EmpaqueInscripcion(String accion, String ida, String baucher) {
        this.accion = accion;
        this.ida = ida;
        this.baucher = baucher;
    }
    String packageData(){

        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("1",ida);
            jo.put("2",baucher);
            Boolean primervalor = true;
            Iterator it = jo.keys();
            do {
                String key=it.next().toString();
                String value=jo.get(key).toString();
                if(primervalor){
                    primervalor = false;
                }else{
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));
            }while (it.hasNext());

            return sb.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
