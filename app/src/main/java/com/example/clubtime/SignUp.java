package com.example.clubtime;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;





public class SignUp extends AppCompatActivity implements View.OnClickListener {
    EditText et_Nombre;
    EditText et_apellPat;
    EditText et_apellMat;
    EditText et_ResEmail;
    EditText et_ResPass;
    Button btRegitrar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Inicializar componentes de la actividad
        et_Nombre =  findViewById(R.id.et_nombre);
        et_apellPat =  findViewById(R.id.et_ApellPat);
        et_apellMat =  findViewById(R.id.et_apellidoMat);
        et_ResEmail =  findViewById(R.id.et_correo);
        et_ResPass =  findViewById(R.id.et_contra);
        btRegitrar =  findViewById(R.id.bt_Registra);




        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        //Agregar accion al oprimir "Registrar"
        btRegitrar.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.bt_Registra){
            String nombre=et_Nombre.getText().toString();
            String apellido_paterno=et_apellPat.getText().toString();
            String apellido_materno=et_apellMat.getText().toString();
            String correo=et_ResEmail.getText().toString();
            String pass=et_ResPass.getText().toString();
            if(nombre.equals("") || apellido_paterno.equals("")|| apellido_materno.equals("") || correo.equals("")){
                if(nombre.equals("")){
                    et_Nombre.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                }
                if(apellido_materno.equals("")){
                    et_apellMat.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                }
                if(apellido_paterno.equals("")){
                    et_apellPat.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                }
                if(nombre.equals("")){
                    et_ResEmail.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                }
                Toast.makeText(getApplicationContext(),"Los campos no pueden estar vacios",Toast.LENGTH_LONG).show();
            }else if(pass.length()<7){
                Toast.makeText(getApplicationContext(),"La contraseña debe ser minimo de 7 caracteres",Toast.LENGTH_LONG).show();
                et_ResPass.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                et_Nombre.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.DARKEN);
                et_ResEmail.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.DARKEN);
                et_apellPat.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.DARKEN);
                et_apellMat.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.DARKEN);

            }else {
                registrarUser("https://clubescom.000webhostapp.com/consultas.php?nombre="+nombre+"&pass="+pass+"&tipo=registro"+"&apellido_mat="+apellido_materno+"&apellido_pat="+apellido_paterno+"&correo="+correo);

            }

            JSONArray array=new JSONArray();
            JSONObject obj=new JSONObject();

            //prueba();

        }


    }

    private void registrarUser (String url){

        JSONArray data2=new JSONArray();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String nombre = null;

                try {
                    jsonObject = response.getJSONObject(0);
                    nombre=jsonObject.getString("nombre_usuario");


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"error1",Toast.LENGTH_LONG).show();
                }
                if(nombre.equals("registrado")){
                    Toast.makeText(getApplicationContext(),"Registro Exitoso",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),LogIn.class);
                    //String message = editText.getText().toString();
                    //intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);

                }else if(nombre.equals("existente")){
                    Toast.makeText(getApplicationContext(),"El correo ya se encuentra registrado",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error al registrar usuario",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                if (error instanceof TimeoutError)  {
                    Toast.makeText(getApplicationContext(),
                            "tiempo ",
                            Toast.LENGTH_LONG).show();
                }else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),
                            "o no conexion",
                            Toast.LENGTH_LONG).show();

                    error.printStackTrace();
                }
                else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(),
                            "AuthFailureError",
                            Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),
                            "ServerError",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "NetworkError",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(),
                            "ParseError",
                            Toast.LENGTH_LONG).show();
                    error.printStackTrace();
                }
            }
        });


        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }
}