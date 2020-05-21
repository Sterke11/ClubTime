package com.example.clubtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.*;

public class LogIn extends AppCompatActivity implements View.OnClickListener{

    TextView tvRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializar componentes de la actividad
        tvRegistrarse = (TextView) findViewById(R.id.tvRegistrarse);

        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        //Poner el texto "Registrarse" subrayado
        SpannableString mitextoU = new SpannableString("Registrarse");
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        tvRegistrarse.setText(mitextoU);

        //Agregar accion al oprimir "Registrar"
        tvRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvRegistrarse){
            Intent intent = new Intent(this, Registrarse.class);
            //String message = editText.getText().toString();
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }
}
