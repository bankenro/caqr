package com.example.globaltics.caqr.Clases.Spinners.Departamento;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.globaltics.caqr.Clases.DatosDatos;
import com.example.globaltics.caqr.Clases.Spinners.Provincias.RecibirProvincias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sam on 19/08/2017.
 */

public class AnalizadorDepartamentos extends AsyncTask<Void,Void,Integer> {
    ArrayList<String> nod = new ArrayList<>();
    ArrayList<String> idd = new ArrayList<>();
    private Context c;
    private String urla,accion2,accion3,s;
    private Spinner departamento,provincia,distrito;
    public AnalizadorDepartamentos(Context c, String urla, String accion2, String accion3, Spinner departamento, Spinner provincia, Spinner distrito, String s) {
        this.c = c;
        this.urla = urla;
        this.accion2 = accion2;
        this.accion3 = accion3;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.s = s;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return this.analizador();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer ==0){
            Toast.makeText(c,"No se pudo analizar los datos", Toast.LENGTH_SHORT).show();
        }else {
            ArrayAdapter<String> a = new ArrayAdapter<String>(c,android.R.layout.simple_expandable_list_item_1,nod);
            departamento.setAdapter(a);

            departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                    Toast.makeText(c,nod.get(i), Toast.LENGTH_SHORT).show();
                    DatosDatos datosDatos = new DatosDatos();
                    datosDatos.setDepartamentos(idd.get(i));
                    new RecibirProvincias(c,urla,idd.get(i),provincia,distrito,accion2,accion3).execute();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private Integer analizador() {
        try{
            JSONArray ja = new JSONArray(s);
            JSONObject jo;
            nod.clear();
            idd.clear();
            for (int i=0;i<ja.length();i++){
                jo = ja.getJSONObject(i);
                String id = jo.getString("codigo");
                String nombre = jo.getString("nombre");

                nod.add(nombre);
                idd.add(id);
            }
            return 1;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return 0;
    }
}
