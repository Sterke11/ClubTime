package com.example.clubtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class InicioAlumno extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rvClubAlumno;
    FloatingActionButton btAgregarClub;
    FloatingActionButton btInforme;
    ConexionDB conexionDB;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_alumno);

        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        //Inicializamos todos los elementos de la actividad
        btAgregarClub = findViewById(R.id.btAgregarClub);
        btInforme = findViewById(R.id.btInforme);
        rvClubAlumno = findViewById(R.id.rvClubAlumno);

        conexionDB = new ConexionDB();

        conexionDB.getClubs(usuario, rvClubAlumno,getApplicationContext());

        //Accion de agregar un nuevo club desde perfil alumno
        btAgregarClub.setOnClickListener(this);
        btInforme.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btAgregarClub){
            Intent intent = new Intent(this,UnirClub.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }
        else if(v.getId() == R.id.btInforme){
            Intent intent = new Intent(this, InformeAlumno.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }

    }
}
