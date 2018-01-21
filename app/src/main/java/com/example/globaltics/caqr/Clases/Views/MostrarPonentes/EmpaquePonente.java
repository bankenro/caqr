package com.example.globaltics.caqr.Clases.Views.MostrarPonentes;

import android.support.v4.widget.SwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PipedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by GlobalTIC's on 10/09/2017.
 */

public class EmpaquePonente {
    private String accion;
    public EmpaquePonente(String accion) {
        this.accion = accion;
    }
    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
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
