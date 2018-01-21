package com.example.globaltics.caqr.Clases.Views.MostrarInscrito;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.ActivarInscripcion.ActualizarActivo;
import com.example.globaltics.caqr.Clases.ActualizarTipo.ActualizarTipo;
import com.example.globaltics.caqr.Clases.RegistrarEventos.RegistrarEventos;
import com.example.globaltics.caqr.Clases.RegistrarPonentes.RegistrarPonentes;
import com.example.globaltics.caqr.Clases.Views.ItemClickListener;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * Created by GlobalTIC's on 9/09/2017.
 */

public class AdaptadorInscrito extends RecyclerView.Adapter<CuerpoInscritos> {
    private Context c;
    private ArrayList<Inscrito> inscrito;
    SharedPreferences preferences;
    private String asis [] = {"DESACTIVAR","ACTIVAR"};
    private String tipou [] = {"ASISTENTE","SECRETARI@","ADMINISTRADOR","EXPOSITOR"};
    private  String activoid,accion;
    private TextView fecha,horai,horaf;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
    public AdaptadorInscrito(Context c, ArrayList<Inscrito> inscrito,String accion) {
        this.c = c;
        this.inscrito = inscrito;
        this.preferences = c.getSharedPreferences("datos", Context.MODE_PRIVATE);
        this.accion = accion;
    }

    @Override
    public CuerpoInscritos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_inscrito,parent,false);
        CuerpoInscritos cuerpoInscritos = new CuerpoInscritos(view);
        return cuerpoInscritos;
    }

    @Override
    public void onBindViewHolder(final CuerpoInscritos holder,final int position) {
        Inscrito ins = inscrito.get(position);
        //inscrito-inscritos
        if (Objects.equals(accion,"30b094af00f8a332c6996b28f730f2fc") || Objects.equals(accion,"301a18860e11e5a70468ddc2f36fe34b")) {

            holder.nombre.setText(ins.getNombres());
            holder.codigo.setText(ins.getId2());

            String imagen = ins.getFoto();
            byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
            holder.foto.setImageBitmap(bitmap);

            String asi = "activo";
            String falt = "inactivo";
            if (Objects.equals(ins.getActivo(), "1")) {
                holder.activom.setText(asi);
                holder.activom.setTextColor(ContextCompat.getColor(c, R.color.activo));
            } else {
                holder.activom.setText(falt);
                holder.activom.setTextColor(ContextCompat.getColor(c, R.color.inactivo));
            }
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (Objects.equals(holder.activom.getText().toString(), "activo")) {
                        Toast.makeText(c, "Su inscripcion esta activa", Toast.LENGTH_SHORT).show();
                    } else if (Objects.equals(holder.activom.getText().toString(), "inactivo")) {
                        Toast.makeText(c, "Active su inscripcion", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else if (Objects.equals(accion,"03f996214fba4a1d05a68b18fece8e71") || Objects.equals(accion,"963b0cf42c168d83f6a2a77989fee514")) {
            holder.nombre.setText(ins.getNombres());
            holder.codigo.setText(ins.getId());
            String imagen = ins.getFoto();
            byte[] byteImage = Base64.decode(imagen, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
            holder.foto.setImageBitmap(bitmap);
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(c, "Codigo: "+inscrito.get(position).getId(), Toast.LENGTH_SHORT).show();
                }
            });

        }
            String tipo = preferences.getString("a","");
            if (!Objects.equals(tipo,"15028d82f1f887339fe4d4c9c2b58b5f")){
                holder.menu.setVisibility(View.VISIBLE);
                holder.menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final PopupMenu popupMenu = new PopupMenu(c,view);
                        if (Objects.equals(accion,"301a18860e11e5a70468ddc2f36fe34b")){//inscritos
                            popupMenu.getMenuInflater().inflate(R.menu.menu_activar_inscripcion,popupMenu.getMenu());
                        }else if (Objects.equals(accion,"03f996214fba4a1d05a68b18fece8e71")){//usuarios
                            popupMenu.getMenuInflater().inflate(R.menu.menu_usuarios,popupMenu.getMenu());
                        }else if (Objects.equals(accion,"963b0cf42c168d83f6a2a77989fee514")){//expositores
                            popupMenu.getMenuInflater().inflate(R.menu.menu_expositores,popupMenu.getMenu());
                        }

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.activari:
                                        final Dialog d = new Dialog(c);
                                        d.setContentView(R.layout.dialog_activar_inscripcion);
                                        TextView nombre1 = (TextView) d.findViewById(R.id.nombre);
                                        final TextView codigo1 = (TextView) d.findViewById(R.id.codigo);
                                        Spinner activo = (Spinner) d.findViewById(R.id.activo);
                                        ImageView foto1 = (ImageView) d.findViewById(R.id.foto);
                                        Button enviar = (Button) d.findViewById(R.id.enviar);

                                        nombre1.setText(inscrito.get(position).getNombres());
                                        codigo1.setText(inscrito.get(position).getId2());
                                        String imagen1 = inscrito.get(position).getFoto();
                                        byte[] bytes = Base64.decode(imagen1, Base64.DEFAULT);
                                        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        foto1.setImageBitmap(bitmap1);

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, asis);
                                        activo.setAdapter(adapter);
                                        activo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i1, long l) {
                                                activoid = Integer.toString(i1);
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                            }
                                        });
                                        enviar.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String accion = MD5.encrypt("act");
                                                new ActualizarActivo(c,urla,accion,activoid,inscrito.get(position).getId(),inscrito.get(position).getId2(),d,holder.activom).execute();
                                            }
                                        });

                                        d.show();
                                        return true;
                                    case R.id.tu:
                                        final Dialog d2 = new Dialog(c);
                                        d2.setContentView(R.layout.dialog_tipo_user);
                                        TextView nombre2 = (TextView) d2.findViewById(R.id.nombre);
                                        final TextView codigo2 = (TextView) d2.findViewById(R.id.codigo);
                                        Spinner activo2 = (Spinner) d2.findViewById(R.id.activo);
                                        ImageView foto2 = (ImageView) d2.findViewById(R.id.foto);
                                        Button enviar2 = (Button) d2.findViewById(R.id.enviar);

                                        nombre2.setText(inscrito.get(position).getNombres());
                                        codigo2.setText(inscrito.get(position).getId());
                                        String imagen2 = inscrito.get(position).getFoto();
                                        byte[] bytes2 = Base64.decode(imagen2, Base64.DEFAULT);
                                        Bitmap bitmap2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                                        foto2.setImageBitmap(bitmap2);

                                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, tipou);
                                        activo2.setAdapter(adapter2);
                                        activo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i1, long l) {
                                                activoid = Integer.toString(i1+1);
                                            }
                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                            }
                                        });
                                        enviar2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String accion = MD5.encrypt("actut");
                                                new ActualizarTipo(c,urla,accion,activoid,inscrito.get(position).getId(),d2).execute();
                                            }
                                        });

                                        d2.show();
                                        return true;

                                    case R.id.re:
                                        final Dialog d3 = new Dialog(c);
                                        d3.setContentView(R.layout.dialog_registrar_expositores);
                                        TextView nombre3 = (TextView) d3.findViewById(R.id.nombre);
                                        final EditText text3 = (EditText) d3.findViewById(R.id.text);
                                        final EditText pais3 = (EditText) d3.findViewById(R.id.pais);
                                        ImageView foto3 = (ImageView) d3.findViewById(R.id.foto);
                                        Button reg3 = (Button) d3.findViewById(R.id.reg);

                                        nombre3.setText(inscrito.get(position).getNombres());
                                        String imagen3 = inscrito.get(position).getFoto();
                                        byte[] bytes3 = Base64.decode(imagen3, Base64.DEFAULT);
                                        Bitmap bitmap3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                                        foto3.setImageBitmap(bitmap3);


                                        reg3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String accion = MD5.encrypt("regex");
                                                new RegistrarPonentes(c,urla,accion,inscrito.get(position).getId(),text3.getText().toString(),pais3.getText().toString(),d3).execute();
                                            }
                                        });

                                        d3.show();
                                        return true;
                                    case R.id.rc:
                                        final Dialog d4 = new Dialog(c);
                                        d4.setContentView(R.layout.dialog_registrar_cronograma);
                                        TextView nombre4 = (TextView) d4.findViewById(R.id.nombre);
                                        final EditText text4 = (EditText) d4.findViewById(R.id.titulo);
                                        fecha = (TextView) d4.findViewById(R.id.fecha);
                                        horai = (TextView) d4.findViewById(R.id.horai);
                                        horaf = (TextView) d4.findViewById(R.id.horaf);
                                        ImageView foto4 = (ImageView) d4.findViewById(R.id.foto);
                                        Button reg4 = (Button) d4.findViewById(R.id.reg);


                                        fecha.setText(sdf.format(calendar.getTime()));
                                        horai.setText(sdf1.format(calendar.getTime())+":00");
                                        horaf.setText(sdf1.format(calendar.getTime())+":00");
                                        nombre4.setText(inscrito.get(position).getNombres());
                                        String imagen4 = inscrito.get(position).getFoto();
                                        byte[] bytes4 = Base64.decode(imagen4, Base64.DEFAULT);
                                        Bitmap bitmap4 = BitmapFactory.decodeByteArray(bytes4, 0, bytes4.length);
                                        foto4.setImageBitmap(bitmap4);

                                        fecha.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                date();
                                            }
                                        });
                                        horai.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                time();
                                            }
                                        });
                                        horaf.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                time1();
                                            }
                                        });

                                        reg4.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String accion = MD5.encrypt("regcr");
                                                new RegistrarEventos(c,urla,accion,inscrito.get(position).getId(),fecha.getText().toString(),
                                                        horai.getText().toString(),horaf.getText().toString(),text4.getText().toString(),d4).execute();
                                            }
                                        });

                                        d4.show();
                                        return true;
                                    case R.id.rt:
                                        final Dialog d5 = new Dialog(c);
                                        d5.setContentView(R.layout.dialog_registrar_talleres);
                                        TextView nombre5 = (TextView) d5.findViewById(R.id.nombre);
                                        final EditText text5 = (EditText) d5.findViewById(R.id.titulo);
                                        fecha = (TextView) d5.findViewById(R.id.fecha);
                                        horai = (TextView) d5.findViewById(R.id.horai);
                                        horaf = (TextView) d5.findViewById(R.id.horaf);
                                        ImageView foto5 = (ImageView) d5.findViewById(R.id.foto);
                                        Button reg5 = (Button) d5.findViewById(R.id.reg);


                                        fecha.setText(sdf.format(calendar.getTime()));
                                        horai.setText(sdf1.format(calendar.getTime())+":00");
                                        horaf.setText(sdf1.format(calendar.getTime())+":00");
                                        nombre5.setText(inscrito.get(position).getNombres());
                                        String imagen5 = inscrito.get(position).getFoto();
                                        byte[] bytes5 = Base64.decode(imagen5, Base64.DEFAULT);
                                        Bitmap bitmap5 = BitmapFactory.decodeByteArray(bytes5, 0, bytes5.length);
                                        foto5.setImageBitmap(bitmap5);

                                        fecha.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                date();
                                            }
                                        });
                                        horai.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                time();
                                            }
                                        });
                                        horaf.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                time1();
                                            }
                                        });
                                        reg5.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String accion = MD5.encrypt("regta");
                                                new RegistrarEventos(c,urla,accion,inscrito.get(position).getId(),fecha.getText().toString(),
                                                        horai.getText().toString(),horaf.getText().toString(),text5.getText().toString(),d5).execute();
                                            }
                                        });

                                        d5.show();
                                        return true;
                                    default:
                                        return onMenuItemClick(menuItem);
                                }
                            /*else if (idm == R.id.modificar){
                            *//*Bundle bundle = new Bundle();
                            Fragment fragment = new ActualizarPerfilFragment();
                            fragment.setArguments(bundle);
                            FragmentManager fragmentManager = ((FragmentActivity) c).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.contenedorn, fragment);
                            fragmentTransaction.addToBackStack(null).commit();*//*
                                return true;
                            }*/

                            }

                        });
                        popupMenu.show();
                    }

                    private void date() {
                        new DatePickerDialog(c,d,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){

                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            calendar.set(Calendar.YEAR,year);
                            calendar.set(Calendar.MONTH,monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                            updatedate();
                        }
                    };

                    private void updatedate() {
                        fecha.setText(sdf.format(calendar.getTime()));
                    }
                    private void time(){
                        new TimePickerDialog(c,d1,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
                    }
                    TimePickerDialog.OnTimeSetListener d1 = new TimePickerDialog.OnTimeSetListener(){

                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            calendar.set(Calendar.HOUR_OF_DAY,i);
                            calendar.set(Calendar.MINUTE,i1);
                            updatetime();
                        }
                    };
                    private void updatetime() {
                        horai.setText(sdf1.format(calendar.getTime())+":00");
                    }
                    private void time1(){
                        new TimePickerDialog(c,d2,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true).show();
                    }
                    TimePickerDialog.OnTimeSetListener d2 = new TimePickerDialog.OnTimeSetListener(){

                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            calendar.set(Calendar.HOUR_OF_DAY,i);
                            calendar.set(Calendar.MINUTE,i1);
                            updatetime1();
                        }
                    };
                    private void updatetime1() {
                        horaf.setText(sdf1.format(calendar.getTime())+":00");
                    }
                });
            }

    }

    

    @Override
    public int getItemCount() {
        return inscrito.size();
    }
}
