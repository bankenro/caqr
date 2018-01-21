package com.example.globaltics.caqr.Clases.Login;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by SamGM on 13/04/2017.
 */

class EmpaqueLogin {
    private String usuario,contraseña;
    EmpaqueLogin(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    String packageData() {
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        String accion= MD5.encrypt("login");
        try{
            jo.put("accion",accion);
            jo.put("username",usuario);
            jo.put("password",contraseña);
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
        }catch (JSONException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
}
