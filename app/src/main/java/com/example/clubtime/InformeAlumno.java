package com.example.clubtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class InformeAlumno extends AppCompatActivity{

    GlobalClass gc;
    ConexionDB conexionDB;
    Usuario usuario;

    RecyclerView rvHoras;
    TextView tvHoras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_alumno);

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        gc = (GlobalClass) getApplicationContext();
        conexionDB = new ConexionDB();

        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

        rvHoras = findViewById(R.id.rvHoras);
        tvHoras = findViewById(R.id.tvHoras);

        conexionDB.getHorasClub(usuario, rvHoras, tvHoras, getApplicationContext());
    }
}