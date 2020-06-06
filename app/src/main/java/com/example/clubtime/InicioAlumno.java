package com.example.clubtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class InicioAlumno extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rvClubs;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button btAgregaClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_alumno);

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        //Inicializamos todos los elementos de la actividad
        rvClubs = (RecyclerView) findViewById(R.id.rvClubs);
        layoutManager = new LinearLayoutManager(this);
        //rvClubs.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        btAgregaClub=findViewById(R.id.btAgregaClub);

        //Accion de agregar un nuevo club desde perfil alumno
        btAgregaClub.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btAgregaClub){
            Intent intent = new Intent(this,UnirClub.class);
            startActivity(intent);
        }

    }
}
