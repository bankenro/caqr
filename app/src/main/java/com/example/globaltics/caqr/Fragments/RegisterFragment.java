package com.example.globaltics.caqr.Fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.globaltics.caqr.Clases.DatosDatos;
import com.example.globaltics.caqr.Clases.Register.EnviarRegistro;
import com.example.globaltics.caqr.Clases.Spinners.Departamento.RecibirDepartamentos;
import com.example.globaltics.caqr.R;
import com.kosalgeek.android.md5simply.MD5;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import static com.example.globaltics.caqr.Activitys.NavigationActivity.urla;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private CheckBox femenino,masculino;
    private EditText fecha,nombre,apellidop,apellidom,correo,dni,codigo,contraseña,telefono;
    private ImageView foto;
    private String tdocumento1;
    private TextView refresh;
    private Spinner departamento,provincia,distrito;
    private String[] tdocumentos = {"DNI", "CARNET EXT.", "RUC", "PASAPORTE", "P. NAC","OTROS"};
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DatosDatos datosDatos = new DatosDatos();
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        refresh = (TextView) view.findViewById(R.id.refresh);
        masculino = (CheckBox)view.findViewById(R.id.maculino);
        femenino = (CheckBox) view.findViewById(R.id.femenino);
        fecha = (EditText) view.findViewById(R.id.fecha);
        nombre = (EditText)view.findViewById(R.id.nombre);
        apellidop = (EditText)view.findViewById(R.id.apellidop);
        apellidom = (EditText)view.findViewById(R.id.apellidom);
        correo = (EditText)view.findViewById(R.id.correo);
        dni = (EditText)view.findViewById(R.id.dni);
        codigo = (EditText)view.findViewById(R.id.codigo);
        contraseña = (EditText)view.findViewById(R.id.contraseña);
        telefono = (EditText)view.findViewById(R.id.telefono);
        foto = (ImageView) view.findViewById(R.id.foto);
        Button registrar = (Button) view.findViewById(R.id.register);
        Spinner spdocumento = (Spinner) view.findViewById(R.id.spdni);



        departamento = (Spinner)view.findViewById(R.id.departamento);
        provincia = (Spinner)view.findViewById(R.id.provincia);
        distrito = (Spinner)view.findViewById(R.id.distrito);


        fecha.setText(sdf.format(calendar.getTime()));

        String accion1=MD5.encrypt("departamentos");
        String accion2 = MD5.encrypt("provincias");
        String accion3 = MD5.encrypt("distritos");
        new RecibirDepartamentos(getActivity(),urla,departamento,provincia,distrito,accion1,accion2,accion3).execute();



        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, tdocumentos);
        spdocumento.setAdapter(adaptador);
        spdocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String nombrei = (String) ((Spinner)adapterView).getSelectedItem();

                tdocumento1 = Integer.toString(i+1);
                Toast.makeText(getActivity(),nombrei,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registrar.setOnClickListener(RegisterFragment.this);
        fecha.setOnClickListener(RegisterFragment.this);
        masculino.setOnClickListener(RegisterFragment.this);
        femenino.setOnClickListener(RegisterFragment.this);
        foto.setOnClickListener(RegisterFragment.this);

        return view;
    }


    private void date() {
        new DatePickerDialog(getActivity(),d,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fecha:
                date();
                break;
            case R.id.femenino:
                masculino.setChecked(false);
                break;
            case R.id.maculino:
                femenino.setChecked(false);
                break;
            case R.id.register:
                registrarnuevo();
                break;
            case R.id.foto:
                sacarfoto();
                break;
        }
    }
    private void sacarfoto() {
        Intent tomarfoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tomarfoto.resolveActivity(getActivity().getPackageManager())!=null){
            startActivityForResult(tomarfoto,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap imagen = (Bitmap) bundle.get("data");
            foto.setImageBitmap(imagen);
        }
    }
    private String convertirfoto(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70,stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat,Base64.NO_WRAP);
        return imgString;
    }

    private void registrarnuevo() {
        String nombre1 = nombre.getText().toString().trim();
        String apellidop1 = apellidop.getText().toString().trim();
        String apellidom1 = apellidom.getText().toString().trim();
        String correo1 =correo.getText().toString().trim();
        foto.buildDrawingCache();
        Bitmap bitmap = foto.getDrawingCache();
        String foto1 = convertirfoto(bitmap);
        String documento1 =dni.getText().toString().trim();
        String sexo1;
        String codigo1 = codigo.getText().toString().trim();
        String contraseña1 = MD5.encrypt(contraseña.getText().toString().trim());
        String telefono1 = telefono.getText().toString().trim();
        String fecha1 = fecha.getText().toString();
        String lnacimiento = datosDatos.getDistrito();
        //String urla ="http://nationfis.hol.es/nonfc/register.php";
        if (!masculino.isChecked() && !femenino.isChecked()){
            Toast.makeText(getActivity(),"Por favor escoja su genero",Toast.LENGTH_SHORT).show();
        }else{
            if(masculino.isChecked()){
                sexo1="1";
            }else{
                sexo1="2";
            }
            try {
                if(nombre1.length() <=0 || apellidop1.length()<=0 || apellidom1.length() <=0 || correo1.length()<=0 || documento1.length()<=0 || sexo1.length()<=0 ||
                        codigo1.length()<=0 || contraseña1.length()<=0 || telefono1.length() <=0 || fecha1.length()<=0 ||
                        foto1.length()<=0 || tdocumento1.length()<=0 || lnacimiento.length()<=0){
                    Toast.makeText(getActivity(),"Rellene todos los campos porfavor",Toast.LENGTH_SHORT).show();
                }else{
                    new EnviarRegistro(getActivity(),urla,nombre1,apellidop1,apellidom1,correo1,documento1,
                            sexo1,codigo1,contraseña1,telefono1,fecha1,foto1,tdocumento1,refresh,lnacimiento).execute();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
