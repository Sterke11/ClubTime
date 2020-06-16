package com.example.clubtime;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class UnirClub extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    ImageView imgSig;
    ImageView imgAnt;

    EditText et_AliasClub;
    EditText et_Pass;

    Usuario usuario;
    ConexionDB conexionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unir_club);

        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        conexionDB = new ConexionDB();

        imgSig = findViewById(R.id.imgSig);
        imgAnt = findViewById(R.id.imgAnt);
        et_AliasClub = findViewById(R.id.et_AliasClub);
        et_Pass = findViewById(R.id.et_Pass);

        et_AliasClub.addTextChangedListener(this);

        imgSig.setOnClickListener(this);
        imgAnt.setOnClickListener(this);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!et_AliasClub.getText().toString().equals(""))  et_AliasClub   .getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if (!et_Pass.getText().toString().equals(""))       et_Pass        .getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==  R.id.imgSig){
            if(et_AliasClub.getText().toString().equals("") || et_Pass.getText().toString().equals("")) {
                if (et_AliasClub.getText().toString().equals(""))  et_AliasClub.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                if (et_Pass.getText().toString().equals(""))       et_Pass.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
            }
            else {
                conexionDB.getClubwithAlias(et_AliasClub.getText().toString(), et_Pass.getText().toString(), usuario, getApplicationContext());
            }
        }
        if(v.getId() == R.id.imgAnt){
            Intent intent = new Intent(getApplicationContext(), InicioAlumno.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void afterTextChanged(Editable s) { }
}