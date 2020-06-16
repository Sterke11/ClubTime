package com.example.clubtime;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;



public class InicioAdmni extends AppCompatActivity implements View.OnClickListener {

    ArrayList<ClubsVo> list;
    RecyclerView rvClubs;
    FloatingActionButton tvCreaClub;
    ConexionDB db;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_admni);
        //GlobalClass gc=(GlobalClass) getApplication();
        usuario = (Usuario) getIntent().getExtras().getSerializable("usuario");

        db = new ConexionDB();

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        tvCreaClub=findViewById(R.id.btAdminCreaClub);
        rvClubs=findViewById(R.id.rvClubs);
        db.getClubs(usuario, rvClubs,getApplicationContext());

        //Accion de agregar un nuevo club desde perfil alumno

        tvCreaClub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btAdminCreaClub){
            Intent intent = new Intent(this, CrearClub.class);
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }
        if (v.getId() == R.id.btAdminAgregaClub){
            Intent intent = new Intent(this, UnirClub.class);
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }

    }
}