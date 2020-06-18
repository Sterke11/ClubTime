package com.example.clubtime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ConexionDB implements Response.ErrorListener {

    Context context;
    Usuario usuario;
    Club club;

    public void iniciarSesion (String nombre, String pass, final Context context){
        String url = "https://clubescom.000webhostapp.com/consultas.php?nombre="+nombre+"&pass="+pass+"&tipo=inicio";
        this.context = context;

        usuario = new Usuario();

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(0);

                        if (!jsonObject.getString("nombre_usuario").equals("none")) {

                            //PASAR DATOS AL OBJETO USUARIO, FAVOR DE CONSULTAR EL SEGUNDO METODO DE LA CLASE ConexionDB
                            usuario = new Usuario(jsonObject);

                            if (usuario.getTipo() == 1){
                                Intent intent = new Intent(context, InicioAdmni.class);
                                //Toast.makeText(context, "none", Toast.LENGTH_LONG).show();
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                //Crear la variable global del usuario activo
                                //GlobalClass gc= (GlobalClass)  context;
                                //gc.setActive_user(usuario);
                                intent.putExtra("usuario", usuario);
                                context.startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(context, InicioAlumno.class);
                                //Toast.makeText(context, "none", Toast.LENGTH_LONG).show();
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                //Crear la variable global del usuario activo
                                //GlobalClass gc= (GlobalClass)  context;
                                //gc.setActive_user(nombre);
                                intent.putExtra("usuario", usuario);
                                context.startActivity(intent);

                            }
                        } else {
                            Toast.makeText(context, "El usuario o contraseña es erroneo", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1", Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void registrarUser(String nombre, String pass, String apellido_paterno, String apellido_materno, String correo, String boleta,int tipo_usuario, final Context context){

        String url = "https://clubescom.000webhostapp.com/consultas.php?nombre="+nombre+"&pass="+pass+"&tipo=registro"+"&apellido_mat="+apellido_materno+"&apellido_pat="+apellido_paterno+"&correo="+correo+"&boleta="+boleta+"&tipo_usuario="+tipo_usuario;
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
                    nombre=jsonObject.getString("respuesta");


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

    public void crearClub(String nombre, String pass , String idClub, final Usuario usuario, final Context context){

        String active_user= usuario.getNombreUser();

        String url = "https://clubescom.000webhostapp.com/consultas.php?nombre="+nombre+"&pass="+pass+"&tipo=crearClub"+"&alias_club="+idClub+"&user_alta="+active_user;
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String resp = "";

                try {
                    jsonObject = response.getJSONObject(0);
                    resp=jsonObject.getString("respuesta");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"error1",Toast.LENGTH_LONG).show();
                }
                if(resp.equals("registrado")){
                    Toast.makeText(context,"Registro Exitoso",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,InicioAdmni.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("usuario", usuario);

                    //String message = editText.getText().toString();
                    //intent.putExtra(EXTRA_MESSAGE, message);
                    context.startActivity(intent);

                }else if(resp.equals("existente")){
                    Toast.makeText(context,"El ID del club ya esta registrado",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context,"Error al registrar club"+resp,Toast.LENGTH_LONG).show();
                }

            }
        }, this);
        requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public void asignarFotoHorarioClub(final Usuario usuario, final Club club, final String[] horario, final String foto, final Context context){
        final ProgressDialog progreso=new ProgressDialog(context);
        //progreso.setMessage("Cargando...");
        //progreso.show();
        final GlobalClass gc= (GlobalClass) context;

        this.context = context;

        String active_user= usuario.getNombreUser();

        String url = "https://clubescom.000webhostapp.com/consultas.php?tipo=asignarFotoHorarioClub";
        this.context = context;

        RequestQueue requestQueue;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progreso.hide();
                if(response.equals("registrado")){

                    getOneClub(gc.getActive_user(),gc.getActive_club(),context);
                    Toast.makeText(context,"Modificacion con exito",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(context,"No se ha podido modificar vuelva a intentarlo mas tarde",Toast.LENGTH_LONG).show();
                }
            }
        },this)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String alias_club=club.getAlias();
                String user_mod=usuario.getNombreUser();
                String correo=usuario.getCorreo();
                String hora_entrada=horario[0];
                String hora_salida=horario[1];
                String imagen=foto;

                Map<String,String> parametros=new HashMap<>();
                parametros.put("alias_club",alias_club);
                parametros.put("user_mod",user_mod);
                parametros.put("hora_entrada",hora_entrada);
                parametros.put("hora_salida",hora_salida);
                parametros.put("imagen",imagen);
                parametros.put("correo",correo);
                return parametros;
            }
        };


        requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void getOneClub(@NotNull final Usuario usuarioArg, Club clubArg, final Context context){

        final GlobalClass gc=new GlobalClass();

        String NomUsuario = usuarioArg.getNombreUser();
        String url = "https://clubescom.000webhostapp.com/consultas.php?Nombre="+NomUsuario+"&tipo=getOneClub"+"&id_club="+clubArg.getID();
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    //String resp = null;
                    //ArrayList<ClubsVo> list=null;
                    ArrayList<Club> list=null;
                    ClubsVo clubsVo=null;
                    AdapterData adapter=null;

                    try {
                        jsonObject = response.getJSONObject(0);

                        //resp = jsonObject.getString("nombre_club");

                        if (!jsonObject.getString("nombre_club").equals("none")) {
                            club= new Club(jsonObject);
                            gc.setActive_club(club);


                        } else {

                            Toast.makeText(context, "No se pudo recuperar el Club", Toast.LENGTH_LONG).show();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1", Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void getClubs(final Usuario usuario, final RecyclerView rvClubs, final Context context){
        //GlobalClass gc= (GlobalClass) context;

        String NomUsuario = usuario.getNombreUser();
        String url = "https://clubescom.000webhostapp.com/consultas.php?Nombre="+NomUsuario+"&tipo=getClubs";
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    JSONObject jsonObject = null;
                    //String resp = null;
                    //ArrayList<ClubsVo> list=null;
                    ArrayList<Club> list=null;
                    ClubsVo clubsVo=null;
                    AdapterData adapter=null;

                    try {
                        jsonObject = response.getJSONObject(0);

                        if (!jsonObject.getString("nombre_club").equals("none")) {
                            club = new Club(jsonObject);
                            Utilidades.status=1;

                            rvClubs.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            //list=new ArrayList<ClubsVo>();
                            list = new ArrayList<Club>();

                            for(int i=0;i<response.length();i++){
                                jsonObject=response.getJSONObject(i);
                                //clubsVo=new ClubsVo(jsonObject.getString("nombre_club"),"Horario de "+jsonObject.getString("hora_entrada")+"a"+jsonObject.getString("hora_salida"),i);
                                club = new Club(jsonObject);
                                list.add(club);
                            }
                            adapter = new AdapterData(list,context);
                            final ArrayList<Club> finalList = list;
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //Toast.makeText(context,"Usted ha seleccionado "+ finalList.get(rvClubs.getChildAdapterPosition(v)).getNombre(),Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context,MenuPrincipalClubAdmin.class);
                                    intent.putExtra("ative_club", finalList.get(rvClubs.getChildAdapterPosition(v)));
                                    intent.putExtra("usuario", usuario);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    //String message = editText.getText().toString();
                                    //intent.putExtra(EXTRA_MESSAGE, message);
                                    context.startActivity(intent);

                                }
                            });
                            rvClubs.setAdapter(adapter);

                        } else {
                            Utilidades.status=0;
                            list = new ArrayList<Club>();
                            list.add(new Club());
                            rvClubs.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            adapter = new AdapterData(list);
                            rvClubs.setAdapter(adapter);
                            //Toast.makeText(context, "Niunguno", Toast.LENGTH_LONG).show();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1", Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void getAdmonClub(final Club club, final RecyclerView rvAdmon, final Context context){
        //GlobalClass gc= (GlobalClass) context;

        int ID = club.getID();
        String url = "https://clubescom.000webhostapp.com/consultas.php?IDClub="+ID+"&tipo=getAdmonClub";
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    JSONObject jsonObject = null;
                    //String resp = null;
                    //ArrayList<ClubsVo> list=null;
                    ArrayList<Usuario> list=null;
                    ClubsVo clubsVo=null;
                    AdapterDataUsuario adapter=null;

                    try {
                        jsonObject = response.getJSONObject(0);

                        if (!jsonObject.getString("nombre_usuario").equals("none")) {
                            usuario = new Usuario(jsonObject);
                            Utilidades.status=1;

                            rvAdmon.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            //list=new ArrayList<ClubsVo>();
                            list = new ArrayList<Usuario>();

                            for(int i=0;i<response.length();i++){
                                jsonObject=response.getJSONObject(i);
                                //clubsVo=new ClubsVo(jsonObject.getString("nombre_club"),"Horario de "+jsonObject.getString("hora_entrada")+"a"+jsonObject.getString("hora_salida"),i);
                                usuario = new Usuario(jsonObject);
                                list.add(usuario);
                            }
                            adapter = new AdapterDataUsuario(list);
                            final ArrayList<Usuario> finalList = list;
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Toast.makeText(context,"Usted ha seleccionado "+ finalList.get(rvAdmon.getChildAdapterPosition(v)).getNombreUser(),Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(context,MenuPrincipalClubAdmin.class);
//                                    intent.putExtra("ative_club", finalList.get(rvAdmon.getChildAdapterPosition(v)));
//                                    intent.putExtra("usuario", usuario);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                    //String message = editText.getText().toString();
//                                    //intent.putExtra(EXTRA_MESSAGE, message);
//                                    context.startActivity(intent);
                                }
                            });
                            rvAdmon.setAdapter(adapter);

                        } else {
                            Utilidades.status=0;
                            list = new ArrayList<Usuario>();
                            list.add(new Usuario());
                            rvAdmon.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            adapter = new AdapterDataUsuario(list);
                            rvAdmon.setAdapter(adapter);
                            Toast.makeText(context, "Ninguno", Toast.LENGTH_LONG).show();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1" + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void getUsuariosClub(final Club club, final RecyclerView rvUsuario, final Context context){
        //GlobalClass gc= (GlobalClass) context;

        int ID = club.getID();
        String url = "https://clubescom.000webhostapp.com/consultas.php?IDClub="+ID+"&tipo=getUsuariosClub";
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    JSONObject jsonObject = null;
                    //String resp = null;
                    //ArrayList<ClubsVo> list=null;
                    ArrayList<Usuario> list=null;
                    ClubsVo clubsVo=null;
                    AdapterDataUsuario adapter=null;

                    try {
                        jsonObject = response.getJSONObject(0);

                        if (!jsonObject.getString("nombre_usuario").equals("none")) {
                            usuario = new Usuario(jsonObject);
                            Utilidades.status=1;

                            rvUsuario.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            //list=new ArrayList<ClubsVo>();
                            list = new ArrayList<Usuario>();

                            for(int i=0;i<response.length();i++){
                                jsonObject=response.getJSONObject(i);
                                //clubsVo=new ClubsVo(jsonObject.getString("nombre_club"),"Horario de "+jsonObject.getString("hora_entrada")+"a"+jsonObject.getString("hora_salida"),i);
                                usuario = new Usuario(jsonObject);
                                list.add(usuario);
                            }
                            adapter = new AdapterDataUsuario(list);
                            final ArrayList<Usuario> finalList = list;
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Toast.makeText(context,"Usted ha seleccionado "+ finalList.get(rvUsuario.getChildAdapterPosition(v)).getNombreUser(),Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(context,MenuPrincipalClubAdmin.class);
//                                    intent.putExtra("ative_club", finalList.get(rvAdmon.getChildAdapterPosition(v)));
//                                    intent.putExtra("usuario", usuario);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                    //String message = editText.getText().toString();
//                                    //intent.putExtra(EXTRA_MESSAGE, message);
//                                    context.startActivity(intent);
                                }
                            });
                            rvUsuario.setAdapter(adapter);

                        } else {
                            Utilidades.status=0;
                            list = new ArrayList<Usuario>();
                            list.add(new Usuario());
                            rvUsuario.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            adapter = new AdapterDataUsuario(list);
                            rvUsuario.setAdapter(adapter);
                            Toast.makeText(context, "Ninguno", Toast.LENGTH_LONG).show();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1" + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void getUsuariosClubCorreo(final Club club, final RecyclerView rvUsuario, final Context context){
        //GlobalClass gc= (GlobalClass) context;

        int ID = club.getID();
        String url = "https://clubescom.000webhostapp.com/consultas.php?IDClub="+ID+"&tipo=getUsuariosClub";
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    JSONObject jsonObject = null;
                    //String resp = null;
                    //ArrayList<ClubsVo> list=null;
                    ArrayList<Usuario> list=null;
                    ClubsVo clubsVo=null;
                    AdapterDataAlumnoCorreo adapter=null;

                    try {
                        jsonObject = response.getJSONObject(0);

                        if (!jsonObject.getString("nombre_usuario").equals("none")) {
                            usuario = new Usuario(jsonObject);
                            Utilidades.status=1;

                            rvUsuario.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            //list=new ArrayList<ClubsVo>();
                            list = new ArrayList<Usuario>();

                            for(int i=0;i<response.length();i++){
                                jsonObject=response.getJSONObject(i);
                                //clubsVo=new ClubsVo(jsonObject.getString("nombre_club"),"Horario de "+jsonObject.getString("hora_entrada")+"a"+jsonObject.getString("hora_salida"),i);
                                usuario = new Usuario(jsonObject);
                                list.add(usuario);
                            }
                            adapter = new AdapterDataAlumnoCorreo(list);
                            final ArrayList<Usuario> finalList = list;
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Toast.makeText(context,"Usted ha seleccionado "+ finalList.get(rvUsuario.getChildAdapterPosition(v)).getNombreUser(),Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(context,MenuPrincipalClubAdmin.class);
//                                    intent.putExtra("ative_club", finalList.get(rvAdmon.getChildAdapterPosition(v)));
//                                    intent.putExtra("usuario", usuario);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                    //String message = editText.getText().toString();
//                                    //intent.putExtra(EXTRA_MESSAGE, message);
//                                    context.startActivity(intent);
                                }
                            });
                            rvUsuario.setAdapter(adapter);

                        } else {
                            Utilidades.status=0;
                            list = new ArrayList<Usuario>();
                            list.add(new Usuario());
                            rvUsuario.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            adapter = new AdapterDataAlumnoCorreo(list);
                            rvUsuario.setAdapter(adapter);
                            Toast.makeText(context, "Ninguno", Toast.LENGTH_LONG).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1" + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void getClubwithAlias(String alias, String pass, final Usuario usuario, final Context context){

        String url = "https://clubescom.000webhostapp.com/consultas.php?tipo=getClubwithAlias"+"&Alias="+alias+"&Pass="+pass;
        this.context = context;
        this.usuario = usuario;

        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    //String resp = null;
                    //ArrayList<ClubsVo> list=null;
                    ArrayList<Club> list=null;
                    ClubsVo clubsVo=null;
                    AdapterData adapter=null;

                    try {
                        jsonObject = response.getJSONObject(0);

                        //resp = jsonObject.getString("nombre_club");

                        if (!jsonObject.getString("nombre_club").equals("none")) {
                            club= new Club(jsonObject);
                            unirClubAlumno(usuario, context);
                        } else {
                            Toast.makeText(context, "El alias y/o la contraseña son incorrectos", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1", Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void unirClubAlumno(final Usuario usuario, final Context context){
        String url = "https://clubescom.000webhostapp.com/consultas.php?id_usuario="+usuario.getID()+"&id_club="+club.getID()+"&tipo=unirClubAlumno";
        this.context = context;

        RequestQueue requestQueue;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String resp = "";

                try {
                    jsonObject = response.getJSONObject(0);
                    resp=jsonObject.getString("respuesta");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"error1",Toast.LENGTH_LONG).show();
                }
                if(resp.equals("registrado")){
                    Toast.makeText(context,"Registro Exitoso. Favor de esperar a que le acepten en el Club.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context,InicioAlumno.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    //String message = editText.getText().toString();
                    intent.putExtra("usuario", usuario);
                    context.startActivity(intent);

                }else if(resp.equals("existente")){
                    Toast.makeText(context,"Usted ya pidio unirse a este Club.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context,"Error al solicitar unirse"+resp,Toast.LENGTH_LONG).show();
                }

            }
        }, this);
        requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public void AgregarAdmiClub(String NombreUser, final Club clb, final RecyclerView rvAdmon, final Context context){

        String active_user= usuario.getNombreUser();

        String url = "https://clubescom.000webhostapp.com/consultas.php?idClub="+clb.getID()+"&NombreUser="+NombreUser+"&tipo=AgregarAdmiClub";
        this.context = context;

        RequestQueue requestQueue;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String resp = "";

                try {
                    jsonObject = response.getJSONObject(0);
                    resp=jsonObject.getString("respuesta");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"error1",Toast.LENGTH_LONG).show();
                }
                if(resp.equals("agregado")){
                    getAdmonClub(clb, rvAdmon, context);
                    Toast.makeText(context,"Administrador agregado al club.",Toast.LENGTH_LONG).show();
                } else if(resp.equals("NoExiste")){
                    Toast.makeText(context,"El nombre de usuario es erroneo",Toast.LENGTH_LONG).show();
                } else if(resp.equals("existente")){
                    Toast.makeText(context,"El administrador ya esta en el club",Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(context,"Error al registrar administrador: "+resp,Toast.LENGTH_LONG).show();
                }

            }
        }, this);
        requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public void EliminarAdmiClub(String NombreUser, final Club clb, final RecyclerView rvAdmon, final Context context){

        String active_user= usuario.getNombreUser();

        String url = "https://clubescom.000webhostapp.com/consultas.php?NombreUser="+NombreUser+"&idClub="+clb.getID()+"&tipo=EliminarAdmiClub";
        this.context = context;

        RequestQueue requestQueue;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String resp = "";

                try {
                    jsonObject = response.getJSONObject(0);
                    resp=jsonObject.getString("respuesta");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"error1",Toast.LENGTH_LONG).show();
                }
                if(resp.equals("Eliminado")){
                    getAdmonClub(clb, rvAdmon, context);
                    Toast.makeText(context,"Administrador eliminado correctamente.",Toast.LENGTH_LONG).show();

                } else if(resp.equals("NoExisteAdmi")){
                    Toast.makeText(context,"El administrador que desea eliminar no existe",Toast.LENGTH_LONG).show();
                } else if(resp.equals("NoExisteUsuario")){
                    Toast.makeText(context,"El nombre de usuario ingresado es erroneo.",Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(context,"Error al eliminar administrador: "+resp,Toast.LENGTH_LONG).show();
                }

            }
        }, this);
        requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public void CambiarEstadoClub(final Club clb, final String Estado, final View root){
        String url = "https://clubescom.000webhostapp.com/consultas.php?idClub="+clb.getID()+"&Estado="+Estado+"&tipo=CambiarEstadoClub";
        this.context = root.getContext();
        final TextView tv_Clase = root.findViewById(R.id.tv_Clase);
        final TextView tv_codigoGenerado = root.findViewById(R.id.tv_codigoGenerado);
        final Button bt_generaCodigo = root.findViewById(R.id.bt_generaCodigo);

        RequestQueue requestQueue;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String resp = "";

                try {
                    jsonObject = response.getJSONObject(0);
                    resp=jsonObject.getString("respuesta");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"error1",Toast.LENGTH_LONG).show();
                }
                if(resp.equals("Actualizado")){
                    if(clb.getEstado().equals("000000")) {
                        tv_Clase.setVisibility(View.VISIBLE);
                        String text = "El codigo generado para la sesión es:\n" + Estado;
                        tv_codigoGenerado.setText(text);
                        text = "Terminar clase";
                        bt_generaCodigo.setText(text);
                        Toast.makeText(context, "Clase iniciada.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        tv_Clase.setVisibility(View.INVISIBLE);
                        String text = "Aun no se ha generado un codigo para la clase";
                        tv_codigoGenerado.setText(text);
                        text = "Generar Codigo";
                        bt_generaCodigo.setText(text);
                        Toast.makeText(context, "Clase finalizada.", Toast.LENGTH_LONG).show();
                    }

                    clb.setEstado(Estado);
                } else{
                    Toast.makeText(context,"Error al generar codigo: "+resp,Toast.LENGTH_LONG).show();
                }

            }
        }, this);
        requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);


    }

    public void PaseLista(final Club clb, final Usuario usr, final String Codigo, final View root){

        String tiempo = "1:30:00";
        String url = "https://clubescom.000webhostapp.com/consultas.php?codigo="+Codigo+"&id_club="+clb.getID()+"&id_usuario="+usr.getID()+"&tiempo="+tiempo+"&tipo=PaseLista";
        this.context = root.getContext();

        final TextView tvEsperar = root.findViewById(R.id.tvEsperar);
        final TextView tvCodigo = root.findViewById(R.id.tvCodigo);
        final TextView etCodigo = root.findViewById(R.id.etCodigo);
        final TextView btCodigo = root.findViewById(R.id.btCodigo);

        RequestQueue requestQueue;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String resp = "";

                try {
                    jsonObject = response.getJSONObject(0);
                    resp=jsonObject.getString("respuesta");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"error1",Toast.LENGTH_LONG).show();
                }
                if(resp.equals("Agregado")){
                    tvCodigo.setVisibility(View.INVISIBLE);
                    etCodigo.setVisibility(View.INVISIBLE);
                    btCodigo.setVisibility(View.INVISIBLE);
                    String text = "¡Ya tomaste asistencia el dia de hoy!";
                    tvEsperar.setVisibility(View.VISIBLE);
                    tvEsperar.setText(text);
                    Toast.makeText(context, "Pase de Lista Completado.", Toast.LENGTH_LONG).show();
                }
                else if(resp.equals("Codigo")){
                    Toast.makeText(context, "Codigo de clase erroneo.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context,"Error al insertar PaseLista: "+resp,Toast.LENGTH_LONG).show();
                }

            }
        }, this);
        requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
    }

    public void getClubId(final Club clb, final Usuario usr, final GlobalClass gc, final View root){
        final TextView tvEsperar = root.findViewById(R.id.tvEsperar);
        final TextView tvCodigo = root.findViewById(R.id.tvCodigo);
        final TextView etCodigo = root.findViewById(R.id.etCodigo);
        final TextView btCodigo = root.findViewById(R.id.btCodigo);

        context = root.getContext();

        String url = "https://clubescom.000webhostapp.com/consultas.php?id_club="+clb.getID()+"&id_usuario="+usr.getID()+"&tipo=getClubId";

        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(0);

                        //resp = jsonObject.getString("nombre_club");

                        if (!jsonObject.getString("nombre_club").equals("none") && !jsonObject.getString("nombre_club").equals("TodaviaNo")) {
                            club= new Club(jsonObject);
                            gc.setActive_club(club);
                            tvCodigo.setVisibility(View.INVISIBLE);
                            etCodigo.setVisibility(View.INVISIBLE);
                            btCodigo.setVisibility(View.INVISIBLE);
                            String text = "¡Ya tomaste asistencia el dia de hoy!";
                            tvEsperar.setVisibility(View.VISIBLE);
                            tvEsperar.setText(text);
                        }
                        else if(jsonObject.getString("nombre_club").equals("TodaviaNo")){ }
                        else {
                            Toast.makeText(context, "Algo salio mal" , Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1", Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void getHorasClub(final Usuario usr, final RecyclerView rvHoras, final TextView tvHoras, final Context context){
        //GlobalClass gc= (GlobalClass) context;

        String url = "https://clubescom.000webhostapp.com/consultas.php?id_usuario="+usr.getID()+"&tipo=getHorasClub";
        this.context = context;

        JSONArray data2=new JSONArray();
        RequestQueue requestQueue;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    JSONObject jsonObject = null;
                    //String resp = null;
                    //ArrayList<ClubsVo> list=null;
                    ArrayList<String> Nombre=null;
                    ArrayList<String> Horas=null;
                    AdapterDataHorasAlumno adapter=null;

                    int[] Total = {0,0};

                    try {
                        jsonObject = response.getJSONObject(0);

                        if (!jsonObject.getString("Nombre").equals("none")) {
                            Utilidades.status=1;

                            rvHoras.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            //list=new ArrayList<ClubsVo>();
                            Nombre = new ArrayList<String>();
                            Horas = new ArrayList<String>();

                            for(int i=0;i<response.length();i++){
                                jsonObject=response.getJSONObject(i);
                                String aux = jsonObject.getString("Nombre");
                                Nombre.add(aux);
                                aux = jsonObject.getString("NoVeces");

                                int[] valor = {0,30};
                                valor[0] += Integer.parseInt(aux);
                                valor[1] *= Integer.parseInt(aux);
                                valor[0] = valor[0] + (valor[1]/60);
                                valor[1] = valor[1]%60;

                                aux = "";
                                if(valor[0] < 10)
                                    aux += "0" + valor[0] + ":";
                                else
                                    aux += valor[0] + ":";
                                if(valor[1] < 10)
                                    aux += "0" + valor[1] + " hrs.";
                                else
                                    aux += valor[1] + " hrs.";

                                Total[0] += valor[0];
                                Total[1] += valor[1];
                                Horas.add(aux);
                            }
                            adapter = new AdapterDataHorasAlumno(Nombre, Horas);
                            adapter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //Toast.makeText(context,"Usted ha seleccionado "+ finalList.get(rvClubs.getChildAdapterPosition(v)).getNombre(),Toast.LENGTH_LONG).show();
                                    //Intent intent = new Intent(context,MenuPrincipalClubAdmin.class);
                                    //intent.putExtra("ative_club", finalList.get(rvHoras.getChildAdapterPosition(v)));
                                    //intent.putExtra("usuario", usuario);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    //String message = editText.getText().toString();
                                    //intent.putExtra(EXTRA_MESSAGE, message);
                                    //context.startActivity(intent);

                                }
                            });
                            rvHoras.setAdapter(adapter);

                            Total[0] = Total[0] + (Total[1]/60);
                            Total[1] = Total[1]%60;

                            String a = "Horas totales: ";
                            if(Total[0] < 10)
                                a += "0" + Total[0] + ":";
                            else
                                a += Total[0] + ":";
                            if(Total[1] < 10)
                                a += "0" + Total[1] + " hrs.";
                            else
                                a += Total[1] + " hrs.";
                            tvHoras.setText(a);

                        } else {
                            Utilidades.status=0;
                            Nombre = new ArrayList<String>();
                            Nombre.add("");
                            Horas = new ArrayList<String>();
                            Horas.add("");
                            rvHoras.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                            adapter = new AdapterDataHorasAlumno(Nombre, Horas);
                            rvHoras.setAdapter(adapter);

                            String a = "Todavia no tienes horas empleadas.";
                            tvHoras.setText(a);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "error1", Toast.LENGTH_LONG).show();
                    }

                }
            }, this);

            requestQueue = Volley.newRequestQueue(context);

            requestQueue.add(jsonArrayRequest);
        }
        catch (Exception e){
            Toast.makeText(context, "No jalo " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(@NotNull VolleyError error) {
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
