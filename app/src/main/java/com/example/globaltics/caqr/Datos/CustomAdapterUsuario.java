package com.example.globaltics.caqr.Datos;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.Login.ComprobarLogin;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import java.util.ArrayList;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;


/*
 * Created by Sam on 19/04/2017.
 */

public class CustomAdapterUsuario extends BaseAdapter {
    private ArrayList<UsuariosUsuarios> usuarioslist;
    private Context c;
    private int usuarios;
    private LayoutInflater inflater;
    private UsuariosSqlite usuariosSqlite;
    public CustomAdapterUsuario(Context c, int usuarios, ArrayList<UsuariosUsuarios> usuarioslist) {
        this.c = c;
        this.usuarioslist = usuarioslist;
        this.usuarios = usuarios;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return usuarioslist.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarioslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view==null){
            view = inflater.inflate(R.layout.usuarios,viewGroup,false);
        }
        TextView tipo = (TextView)view.findViewById(R.id.tipo);
        TextView nombre = (TextView)view.findViewById(R.id.nombre);
        ImageView imagen = (ImageView)view.findViewById(R.id.imagen);
        final ImageButton menu = (ImageButton)view.findViewById(R.id.menu);

        UsuariosUsuarios usuariosUsuarios = usuarioslist.get(position);
        tipo.setText(usuariosUsuarios.getTipo());
        nombre.setText(usuariosUsuarios.getNombre());
        String image=(usuariosUsuarios.getImagen());
        byte[] byteImage = Base64.decode(image, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
        imagen.setImageBitmap(bitmap);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(c);
                d.setContentView(R.layout.dialoglogin);
                TextView nombre = (TextView)d.findViewById(R.id.nombre);
                TextView tipo = (TextView)d.findViewById(R.id.tipo);
                final TextView refresh = (TextView)d.findViewById(R.id.refresh);
                ImageView imagen = (ImageView)d.findViewById(R.id.imagen);
                Button ingresar = (Button)d.findViewById(R.id.ingresar);
                final EditText password = (EditText)d.findViewById(R.id.password);
                nombre.setText(usuarioslist.get(position).getNombre());
                tipo.setText(usuarioslist.get(position).getTipo());
                String imagen1 = usuarioslist.get(position).getImagen();
                byte[] byteImage = Base64.decode(imagen1, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray( byteImage, 0, byteImage.length);
                imagen.setImageBitmap(bitmap);
                final String codigo = usuarioslist.get(position).getCodigo();
                ingresar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String password1 = MD5.encrypt(password.getText().toString().trim());
                        //String urla = "http://nationfis.hol.es/nonfc/login.php";
                        if (password1.length()<=0){
                            Toast.makeText(c,"Ingrese sus contraseña", Toast.LENGTH_SHORT).show();
                        }else {
                            new ComprobarLogin(c,urla,codigo,password1,refresh).execute();
                        }
                    }
                });
                d.show();
            }
        });
        usuariosSqlite = new UsuariosSqlite(c,"UsersDB.sqlite",null,1);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(c,view);
                popupMenu.getMenuInflater().inflate(R.menu.menuloginforonetouch,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int i = menuItem.getItemId();
                        if (i == R.id.eliminar){
                            try{
                                usuariosSqlite.deleteData(usuarioslist.get(position).getCodigo());
                                Toast.makeText(c,"Usuario Eliminado "+usuarioslist.get(position).getCodigo(), Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            usuarioslist.remove(position);
                            notifyDataSetChanged();
                            return true;
                        }else{
                            return onMenuItemClick(menuItem);
                        }
                    }
                });
                popupMenu.show();
            }
        });
        return view;
    }

}
