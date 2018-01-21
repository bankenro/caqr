package com.example.globaltics.caqr.Clases.RegistrarAsistencia;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by GlobalTIC's on 5/09/2017.
 */

public class EmpaqueRAsistencia {
    private String accion,mensaje0,fecha;
    private Date today = Calendar.getInstance().getTime();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
    public EmpaqueRAsistencia(String accion, String mensaje0) {
        this.accion = accion;
        this.mensaje0 = mensaje0;

    }
    String packageData(){
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        fecha = formatter.format(today);
        try {
            jo.put("accion",accion);
            jo.put("1",mensaje0);
            jo.put("2",fecha);
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
