package com.example.clubtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class UnirClub extends AppCompatActivity implements View.OnClickListener {

    ImageView imgSig;
    ImageView imgAnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unir_club);

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        imgSig = findViewById(R.id.imgSig);
        imgAnt = findViewById(R.id.imgAnt);

        imgSig.setOnClickListener(this);
        imgAnt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==  R.id.imgSig){

        }
        if(v.getId() == R.id.imgAnt){
            Intent intent = new Intent(getApplicationContext(), InicioAlumno.class);
            startActivity(intent);
        }
    }
}