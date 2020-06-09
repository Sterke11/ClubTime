package com.example.clubtime;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    EditText et_Nombre;
    EditText et_apellPat;
    EditText et_apellMat;
    EditText et_ResEmail;
    EditText et_ResPass;
    EditText et_boleta;
    Button btRegistrar;
    CheckBox chbAlumno;
    CheckBox chbAdministrador;

    ConexionDB conexionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Inicializar componentes de la actividad
        et_Nombre = findViewById(R.id.et_nombre);
        et_apellPat = findViewById(R.id.et_ApellPat);
        et_apellMat = findViewById(R.id.et_apellidoMat);
        et_ResEmail = findViewById(R.id.et_correo);
        et_ResPass = findViewById(R.id.et_contra);
        et_boleta = findViewById(R.id.et_boleta);
        btRegistrar = findViewById(R.id.bt_Registra);
        chbAlumno = findViewById(R.id.chbAlumno);
        chbAdministrador = findViewById(R.id.chbAdministrador);

        et_Nombre  .addTextChangedListener(this);
        et_apellMat.addTextChangedListener(this);
        et_apellPat.addTextChangedListener(this);
        et_ResEmail.addTextChangedListener(this);
        et_ResPass .addTextChangedListener(this);
        et_boleta  .addTextChangedListener(this);

        conexionDB = new ConexionDB();

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        //Agregar accion al oprimir "Registrar"
        btRegistrar     .setOnClickListener(this);
        chbAlumno       .setOnClickListener(this);
        chbAdministrador.setOnClickListener(this);

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!et_Nombre.equals(""))           et_Nombre  .getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if (!et_apellMat.equals(""))         et_apellMat.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if (!et_apellPat.equals(""))         et_apellPat.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if (!et_ResEmail.equals(""))         et_ResEmail.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if (!et_boleta.equals(""))           et_boleta  .getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if (et_ResPass.getText().length()>6) et_ResPass .getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.bt_Registra){
            String nombre=et_Nombre.getText().toString();
            String apellido_paterno=et_apellPat.getText().toString();
            String apellido_materno=et_apellMat.getText().toString();
            String correo=et_ResEmail.getText().toString();
            String pass=et_ResPass.getText().toString();
            String boleta=et_boleta.getText().toString();
            int tipo_usuario;

            if(nombre.equals("") || apellido_paterno.equals("")|| apellido_materno.equals("") || correo.equals("") || boleta.equals("") ||
                    pass.equals("") ){

                if(nombre.equals(""))           et_Nombre  .getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                if(apellido_materno.equals("")) et_apellMat.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                if(apellido_paterno.equals("")) et_apellPat.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                if(correo.equals(""))           et_ResEmail.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                if(boleta.equals("") && (chbAlumno.isChecked() || !chbAlumno.isChecked() && !chbAdministrador.isChecked()))           et_boleta  .getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                if(pass.equals(""))             et_ResPass .getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                Toast.makeText(getApplicationContext(),"Los campos no pueden estar vacios",Toast.LENGTH_LONG).show();
            }else if(!chbAlumno.isChecked() && !chbAdministrador.isChecked()){
                chbAlumno       .setTextColor(Color.parseColor("#7f7f7f"));
                chbAdministrador.setTextColor(Color.parseColor("#7f7f7f"));

                Toast.makeText(getApplicationContext(),"Debe seleccionar un tipo de usuario, Administrador o Alumno",Toast.LENGTH_LONG).show();
            }
            else if(pass.length()<7){
                Toast.makeText(getApplicationContext(),"La contraseña debe ser minimo de 7 caracteres",Toast.LENGTH_LONG).show();
                et_ResPass.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);

            }else {
                tipo_usuario = (chbAlumno.isChecked())?0:1;
                conexionDB.registrarUser(nombre, pass, apellido_paterno, apellido_materno, correo,boleta,tipo_usuario, getApplicationContext());
                //registrarUser("https://clubescom.000webhostapp.com/consultas.php?nombre="+nombre+"&pass="+pass+"&tipo=registro"+"&apellido_mat="+apellido_materno+"&apellido_pat="+apellido_paterno+"&correo="+correo);

            }

            JSONArray array=new JSONArray();
            JSONObject obj=new JSONObject();

            //prueba();

        }
        if(v.getId() == R.id.chbAlumno){
            chbAdministrador.setChecked(false);
            chbAlumno       .setTextColor(Color.parseColor("#0091BF"));
            chbAdministrador.setTextColor(Color.parseColor("#0091BF"));

        }
        if(v.getId() == R.id.chbAdministrador){
            chbAlumno       .setChecked(false);
            chbAlumno       .setTextColor(Color.parseColor("#0091BF"));
            chbAdministrador.setTextColor(Color.parseColor("#0091BF"));
        }

    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
    @Override
    public void afterTextChanged(Editable s) { }


}