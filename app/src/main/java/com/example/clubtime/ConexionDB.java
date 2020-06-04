package com.example.clubtime;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ConexionDB implements Response.ErrorListener {

    Context context;

    public void iniciarSesion (String nombre, String pass, final Context context){
        String url = "https://clubescom.000webhostapp.com/consultas.php?nombre="+nombre+"&pass="+pass+"&tipo=inicio";
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    String nombre = null;

                    try {
                        jsonObject = response.getJSONObject(0);
                        nombre = jsonObject.getString("nombre_usuario");


                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1", Toast.LENGTH_LONG).show();
                    }
                    if (!nombre.equals("none")) {
                        Intent intent = new Intent(context, InicioAlumno.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "El usuario o contraseña es erroneo", Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);
            Toast.makeText(context, "Todo bien", Toast.LENGTH_LONG).show();
            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void registrarUser(String nombre, String pass, String apellido_paterno, String apellido_materno, String correo, final Context context){

        String url = "https://clubescom.000webhostapp.com/consultas.php?nombre="+nombre+"&pass="+pass+"&tipo=registro"+"&apellido_mat="+apellido_materno+"&apellido_pat="+apellido_paterno+"&correo="+correo;
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;

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
                    Toast.makeText(context,"error1",Toast.LENGTH_LONG).show();
                }
                if(nombre.equals("registrado")){
                    Toast.makeText(context,"Registro Exitoso",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,LogIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //String message = editText.getText().toString();
                    //intent.putExtra(EXTRA_MESSAGE, message);
                    context.startActivity(intent);

                }else if(nombre.equals("existente")){
                    Toast.makeText(context,"El correo ya se encuentra registrado",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context,"Error al registrar usuario",Toast.LENGTH_LONG).show();
                }

            }
        }, this);
        requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        if (error instanceof TimeoutError)  {
            Toast.makeText(context,
                    "tiempo ",
                    Toast.LENGTH_LONG).show();
        }else if (error instanceof NoConnectionError) {
            Toast.makeText(context,
                    "o no conexion",
                    Toast.LENGTH_LONG).show();

            error.printStackTrace();
        }
        else if (error instanceof AuthFailureError) {
            Toast.makeText(context,
                    "AuthFailureError",
                    Toast.LENGTH_LONG).show();
            error.printStackTrace();
        } else if (error instanceof ServerError) {
            Toast.makeText(context,
                    "ServerError",
                    Toast.LENGTH_LONG).show();
        } else if (error instanceof NetworkError) {
            Toast.makeText(context,
                    "NetworkError",
                    Toast.LENGTH_LONG).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(context,
                    "ParseError",
                    Toast.LENGTH_LONG).show();
            error.printStackTrace();
        }
    }
}
