package com.example.globaltics.caqr.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.globaltics.caqr.Fragments.ActivarInscripcion;
import com.example.globaltics.caqr.Fragments.MainFragment;
import com.example.globaltics.caqr.Fragments.MostrarAsistencia;
import com.example.globaltics.caqr.Fragments.MostrarCronograma;
import com.example.globaltics.caqr.Fragments.MostrarPonentes;
import com.example.globaltics.caqr.Fragments.MostrarTalleres;
import com.example.globaltics.caqr.Fragments.RegistrarAsistencia;
import com.example.globaltics.caqr.Fragments.RegistrarInscripcion;
import com.example.globaltics.caqr.R;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String tipo, nombre, image, tipos, codigou;
    private Bitmap foto;
    public static final String urla = "http://192.168.1.75/caqr/entrada.php";
    public static final String TAG = "error";

    //public static final String urla = "https://nationfis.000webhostapp.com/caqr/entrada.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();
        fragmentTransaction.add(R.id.contenedorn, fragment);
        fragmentTransaction.commit();

        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tipo = preferences.getString("a", "");
        nombre = preferences.getString("nombre", "");
        image = preferences.getString("image", "");
        tipos = preferences.getString("tipos", "");
        codigou = preferences.getString("codigo", "");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        switch (tipo) {
            case "36a96348c4720f89e22fd1966ec23c26": //secretari@
                navigationView.getMenu().setGroupVisible(R.id.secre, true);
                break;
            case "91f5167c34c400758115c2a6826ec2e3": //administrador
                navigationView.getMenu().setGroupVisible(R.id.admin, true);
                break;
            case "15028d82f1f887339fe4d4c9c2b58b5f": //asistente
                navigationView.getMenu().setGroupVisible(R.id.asist, true);
                break;
            default:
                break;
        }

        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView nombret = (TextView) view.findViewById(R.id.nombre);
        TextView codigo = (TextView) view.findViewById(R.id.codigo);
        TextView tipot = (TextView) view.findViewById(R.id.tipo);
        ImageView fotot = (ImageView) view.findViewById(R.id.foto);

        nombret.setText(nombre);
        tipot.setText(tipos);
        codigo.setText(codigou);

        byte[] byteImage = Base64.decode(image, Base64.DEFAULT);
        foto = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        fotot.setImageBitmap(foto);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {  /*Closes the Appropriate Drawer*/
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().beginTransaction().commit();
            } else {
                super.onBackPressed();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean fragmentTransaction = false;
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (id) {
            case R.id.activar:
                fragment = new ActivarInscripcion();
                fragmentTransaction = true;
                bundle.putString("accion", "inscritos");
                fragment.setArguments(bundle);
                break;
            case R.id.usuarios:
                fragment = new ActivarInscripcion();
                fragmentTransaction = true;
                bundle.putString("accion", "usuarios");
                fragment.setArguments(bundle);
                break;
            case R.id.expositores:
                fragment = new ActivarInscripcion();
                fragmentTransaction = true;
                bundle.putString("accion", "expositores");
                fragment.setArguments(bundle);
                break;
            case R.id.cronograma:
                fragment = new MostrarCronograma();
                fragmentTransaction = true;
                break;
            case R.id.talleres:
                fragment = new MostrarTalleres();
                fragmentTransaction = true;
                break;
            case R.id.talleresas:
                fragment = new MostrarTalleres();
                fragmentTransaction = true;
                break;
            case R.id.talleresad:
                fragment = new MostrarTalleres();
                fragmentTransaction = true;
                break;
            case R.id.moponentes:
                fragment = new MostrarPonentes();
                fragmentTransaction = true;
                break;
            case R.id.asistencia:
                fragment = new MostrarAsistencia();
                fragmentTransaction = true;
                break;
            case R.id.asistenciar:
                fragment = new RegistrarAsistencia();
                fragmentTransaction = true;
                break;
            case R.id.asistenciaa:
                fragment = new MostrarAsistencia();
                fragmentTransaction = true;
                break;
            case R.id.inscripcionr:
                fragment = new RegistrarInscripcion();
                fragmentTransaction = true;
                break;
            case R.id.cronogramaad:
                fragment = new MostrarCronograma();
                fragmentTransaction = true;
                break;
            case R.id.cronogramaas:
                fragment = new MostrarCronograma();
                fragmentTransaction = true;
                break;
            case R.id.contacto:
                break;
            case R.id.cerrar:
                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(NavigationActivity.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        if (fragmentTransaction) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.contenedorn,fragment).addToBackStack(null).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedorn, fragment).commit();
            item.setChecked(true);
            //getSupportActionBar().setTitle(item.getTitle());
            setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
