package com.example.clubtime;

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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity implements View.OnClickListener{

    TextView tvRegistrarse;
    EditText tvResEmail;
    EditText tvResPass;
    Button btIniciarSesion;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializar componentes de la actividad
        tvRegistrarse = (TextView) findViewById(R.id.tvRegistrarse);
        tvResEmail =  findViewById(R.id.tvResEmail);
        tvResPass =  findViewById(R.id.tvResPass);
        btIniciarSesion =  findViewById(R.id.btIniciarSesion);




        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        //Poner el texto "Registrarse" subrayado
        SpannableString mitextoU = new SpannableString("Registrarse");
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        tvRegistrarse.setText(mitextoU);

        //Agregar accion al oprimir "Registrar"
        tvRegistrarse.setOnClickListener(this);

        //Listener del boton de iniciar sesion (imagen: ->)

        btIniciarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvRegistrarse){
            Intent intent = new Intent(this, SignUp.class);
            //String message = editText.getText().toString();
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
        if(v.getId() == R.id.btIniciarSesion){
            String nombre=tvResEmail.getText().toString();
            String pass=tvResPass.getText().toString();


            JSONArray array=new JSONArray();
            JSONObject obj=new JSONObject();
            try {

                obj.put("nombre",nombre);
                obj.put("pass",pass);
                obj.put("tipo","inicio");

            } catch (Exception e) {
                e.printStackTrace();
            }
            array.put(obj);

            iniciarSesion("https://clubescom.000webhostapp.com/consultas.php?nombre="+nombre+"&pass="+pass+"&tipo=inicio");
            //prueba();

        }
    }

    private void iniciarSesion (String url){

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
                if(!nombre.equals("none")){
                        Intent intent = new Intent(getApplicationContext(),Inicio.class);
                        //String message = editText.getText().toString();
                        //intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"El usuario o contraseña es erroneo",Toast.LENGTH_LONG).show();
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

    /*public static class consultaUser extends AsyncTask<String,Void,String >{
        private WeakReference<Context> context;

        public consultaUser(Context context){
            this.context= new WeakReference<>(context);
        }

        protected String doInBackground(String... params){
         String checar ="https://clubescom.000webhostapp.com/consultas.php";
         String result="";

         try {
             URL url= new URL(checar);
             HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
             httpURLConnection.setRequestMethod("POST");
             httpURLConnection.setDoOutput(true);
             OutputStream outputStream= httpURLConnection.getOutputStream();
             BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

             String nombre =params[0];
             String pass =params[1];
             String tipo="inicio";

             String data= URLEncoder.encode("nombre","UTF-8")+"="+URLEncoder.encode(nombre,"UTF-8")
                     +"&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+
                     "&"+URLEncoder.encode("tipo","UTF-8")+"="+URLEncoder.encode(tipo,"UTF-8");

             bufferedWriter.write(data);
             bufferedWriter.flush();
             bufferedWriter.close();
             outputStream.close();

             InputStream inputStream= httpURLConnection.getInputStream();
             BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));
             StringBuilder stringBuilder = new StringBuilder();


             String line;
             while ((line=bufferedReader.readLine())!=null){
                 stringBuilder.append(line);
             }
             result=stringBuilder.toString();
             bufferedReader.close();
             inputStream.close();
             httpURLConnection.disconnect();



         }catch (Exception e){

         }
         return result;
        }

        protected void onPostExecute(String resultado){
            Toast.makeText(context.get(),resultado,Toast.LENGTH_LONG).show();       }

    }


*/
}
