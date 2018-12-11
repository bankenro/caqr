package com.example.globaltics.caqr.Activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.globaltics.caqr.Datos.UsuariosSqlite;
import com.example.globaltics.caqr.Fragments.LoginForOneTouch;
import com.example.globaltics.caqr.Fragments.LoginFragment;
import com.example.globaltics.caqr.R;

public class Login extends AppCompatActivity {
    private String tipo,nombre,codigo,image,tipos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tipo = preferences.getString("a","");
        nombre = preferences.getString("nombre","");
        codigo = preferences.getString("codigo","");
        image = preferences.getString("image","");
        tipos = preferences.getString("tipos","");

        UsuariosSqlite usuariosSqlite = new UsuariosSqlite(Login.this,"UsersDB.sqlite",null,1);
        usuariosSqlite.queryData();

        Cursor cursor = usuariosSqlite.getData("SELECT * FROM USERS");

        if (ActivityCompat.checkSelfPermission(Login.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // verificamos la version de ANdroid que sea al menos la M para mostrar
                // el dialog de la solicitud de la camara
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CAMERA)) ;
                requestPermissions(new String[]{Manifest.permission.CAMERA},225);
            }
        }

        if(savedInstanceState == null){
            if(tipo.length()>0 || nombre.length()>0 || codigo.length()>0 || image.length()>0 || tipos.length()>0){
                Intent intent = new Intent(this,NavigationActivity.class);
                startActivity(intent);
                finish();
            }else if(cursor.moveToFirst()){
                Fragment fragment = new LoginForOneTouch();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.contenedor,fragment);
                fragmentTransaction.commit();
            }else{
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new LoginFragment();
                fragmentTransaction.add(R.id.contenedor,fragment);
                fragmentTransaction.commit();
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().commit();
        }else {
            super.onBackPressed();
        }
    }
}
