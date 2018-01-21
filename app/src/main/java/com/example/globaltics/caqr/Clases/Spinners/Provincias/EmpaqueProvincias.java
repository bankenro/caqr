package com.example.globaltics.caqr.Clases.Spinners.Provincias;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Sam on 19/08/2017.
 */

public class EmpaqueProvincias {
    private String accion2,id;
    public EmpaqueProvincias(String accion2, String id) {
        this.accion2 = accion2;
        this.id = id;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion2);
            jo.put("1",id);
            Boolean primer = true;
            Iterator i = jo.keys();
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
