package com.example.globaltics.caqr.Clases.RegistrarEventos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class EmpaqueEventos {
    private String accion,id,fecha,horai,horaf,titulo;
    public EmpaqueEventos(String accion, String id, String fecha, String horai, String horaf, String titulo) {
        this.accion = accion;
        this.id = id;
        this.fecha = fecha;
        this.horai = horai;
        this.horaf = horaf;
        this.titulo = titulo;
    }
    String packageData(){

        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("1",id);
            jo.put("2",fecha);
            jo.put("3",horai);
            jo.put("4",horaf);
            jo.put("5",titulo);
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
