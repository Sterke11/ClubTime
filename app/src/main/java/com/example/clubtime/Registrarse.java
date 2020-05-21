package com.example.clubtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Registrarse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();
    }
}
