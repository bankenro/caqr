package com.example.globaltics.caqr.Clases.Register;

import com.kosalgeek.android.md5simply.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by SamGM on 14/04/2017.
 */

class EmpaqueRegistro {
    private String nombre1,apellidop1,apellidom1,correo1,documento1,sexo1,codigo1,contraseña1,telefono1,
            fecha1,foto1,tdocumento1,lnacimiento;
    EmpaqueRegistro(String nombre1, String apellidop1, String apellidom1, String correo1, String documento1, String sexo1,
                    String foto1, String codigo1, String contraseña1, String telefono1, String fecha1,
                    String tdocumento1, String lnacimiento) {
        this.nombre1 = nombre1;
        this.apellidop1 = apellidop1;
        this.apellidom1 = apellidom1;
        this.correo1 = correo1;
        this.documento1 = documento1;
        this.sexo1 = sexo1;
        this.foto1 = foto1;
        this.codigo1 = codigo1;
        this.contraseña1 = contraseña1;
        this.telefono1 = telefono1;
        this.fecha1 = fecha1;
        this.tdocumento1 = tdocumento1;
        this.lnacimiento = lnacimiento;
    }
    String packageData(){
        String accion= MD5.encrypt("registrar");
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();
        try {
            jo.put("accion",accion);
            jo.put("nombre1",nombre1);
            jo.put("apellidop1",apellidop1);
            jo.put("apellidom1",apellidom1);
            jo.put("correo1",correo1);
            jo.put("documento1",documento1);
            jo.put("sexo1",sexo1);
            jo.put("foto1",foto1);
            jo.put("codigo1",codigo1);
            jo.put("password",contraseña1);
            jo.put("telefono1",telefono1);
            jo.put("fecha1",fecha1);
            jo.put("tdocumento1",tdocumento1);
            jo.put("1",lnacimiento);
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
