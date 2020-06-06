package com.example.clubtime;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CrearClub extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText et_IdClub;
    EditText et_Nombre;
    EditText et_Pass;
    Button bt_CreaClub;
    ConexionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_club);
        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        et_Nombre=findViewById(R.id.et_Nombre);
        et_IdClub=findViewById(R.id.et_IdClub);
        et_Pass=findViewById(R.id.et_Pass);
        bt_CreaClub=findViewById(R.id.btCrearClub);

        et_Nombre.addTextChangedListener(this);
        et_IdClub.addTextChangedListener(this);
        et_Pass.addTextChangedListener(this);
        db= new ConexionDB();

        bt_CreaClub.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btCrearClub){
            String nombre=et_Nombre.getText().toString();
            String pass=et_Pass.getText().toString();
            String idClub=et_IdClub.getText().toString();

            if(nombre.equals("") || pass.equals("")|| idClub.equals("")){
                if(nombre.equals(""))           et_Nombre.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                if(idClub.equals(""))       et_IdClub.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                Toast.makeText(getApplicationContext(),"Los campos no pueden estar vacios",Toast.LENGTH_LONG).show();
            }else if(pass.length()<7){
                Toast.makeText(getApplicationContext(),"La contraseña debe ser minimo de 7 caracteres",Toast.LENGTH_LONG).show();
                et_Pass.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);

            }else {

                db.crearClub(et_Nombre.getText().toString(),et_Pass.getText().toString(),et_IdClub.getText().toString(),getApplicationContext());
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!et_Nombre.equals(""))           et_Nombre.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if (!et_Pass.equals(""))         et_Pass.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if (!et_IdClub.equals(""))         et_IdClub.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}