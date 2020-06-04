package com.example.clubtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;

public class InicioAlumno extends AppCompatActivity {

    RecyclerView rvClubs;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_alumno);

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        //Inicializamos todos los elementos de la actividad
        rvClubs = (RecyclerView) findViewById(R.id.rvClubs);
        layoutManager = new LinearLayoutManager(this);
        rvClubs.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)


    }
}
