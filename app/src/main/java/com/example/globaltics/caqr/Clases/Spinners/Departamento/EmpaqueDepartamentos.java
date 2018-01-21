package com.example.globaltics.caqr.Clases.Spinners.Departamento;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Sam on 19/08/2017.
 */

public class EmpaqueDepartamentos {
    private String accion1;
    public EmpaqueDepartamentos(String accion1) {
        this.accion1 = accion1;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion1);
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
