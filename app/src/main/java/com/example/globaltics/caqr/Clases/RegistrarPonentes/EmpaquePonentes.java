package com.example.globaltics.caqr.Clases.RegistrarPonentes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class EmpaquePonentes {
    private String accion,id,texto,pais;
    public EmpaquePonentes(String accion, String id, String texto, String pais) {
        this.accion = accion;
        this.id = id;
        this.texto = texto;
        this.pais = pais;
    }
    String packageData(){

        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("1",id);
            jo.put("2",texto);
            jo.put("3",pais);
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
