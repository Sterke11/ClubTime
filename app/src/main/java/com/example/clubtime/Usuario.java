package com.example.clubtime;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

public class Usuario implements Serializable {

    private int ID;
    private String NombreUser;
    private String Nombre;
    private String ApPaterno;
    private String ApMaterno;
    private String Boleta;
    private String Contra;
    private int Tipo;
    private String FechAlta;
    private String FechBaja;
    private int Status;
    private String UserAlta;
    private String Correo;

    public Usuario(){ }

    public Usuario(@NotNull JSONObject jsonObject) throws JSONException, ParseException {
        setID(jsonObject.getInt("id_usuario"));
        setNombreUser(jsonObject.getString("nombre_usuario"));
        setNombre(jsonObject.getString("nombre"));
        setApPaterno(jsonObject.getString("ap_paterno"));
        setApMaterno(jsonObject.getString("ap_materno"));
        setBoleta(jsonObject.getString("boleta"));
        setContra(jsonObject.getString("contraseña"));
        setTipo(jsonObject.getInt("tipo"));
        setFechAlta(jsonObject.getString("fecha_alta"));
        setFechBaja(jsonObject.getString("fecha_baja"));
        setStatus(jsonObject.getInt("status"));
        setUserAlta(jsonObject.getString("usuario_alta"));
        setCorreo(jsonObject.getString("correo"));
    }

    public Usuario(int ID, String nombreUser, String nombre, String apPaterno, String apMaterno, String boleta, String contra, int tipo, String fechAlta, String fechBaja, int status, String userAlta, String correo) {
        this.ID = ID;
        NombreUser = nombreUser;
        Nombre = nombre;
        ApPaterno = apPaterno;
        ApMaterno = apMaterno;
        Boleta = boleta;
        Contra = contra;
        Tipo = tipo;
        FechAlta = fechAlta;
        FechBaja = fechBaja;
        Status = status;
        UserAlta = userAlta;
        Correo = correo;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public String getNombreUser() { return NombreUser; }

    public void setNombreUser(String nombreUser) { NombreUser = nombreUser; }

    public String getNombre() { return Nombre; }

    public void setNombre(String nombre) { Nombre = nombre; }

    public String getApPaterno() { return ApPaterno; }

    public void setApPaterno(String apPaterno) { ApPaterno = apPaterno; }

    public String getApMaterno() { return ApMaterno; }

    public void setApMaterno(String apMaterno) { ApMaterno = apMaterno; }

    public String getBoleta() { return Boleta; }

    public void setBoleta(String boleta) { Boleta = boleta; }

    public String getContra() { return Contra; }

    public void setContra(String contra) { Contra = contra; }

    public int getTipo() { return Tipo; }

    public void setTipo(int tipo) { Tipo = tipo; }

    public String getFechAlta() { return FechAlta; }

    public void setFechAlta(String fechAlta) { FechAlta = fechAlta; }

    public String getFechBaja() { return FechBaja; }

    public void setFechBaja(String fechBaja) { FechBaja = fechBaja; }

    public int getStatus() { return Status; }

    public void setStatus(int status) { Status = status; }

    public String getUserAlta() { return UserAlta; }

    public void setUserAlta(String userAlta) { UserAlta = userAlta; }

    public String getCorreo() { return Correo; }

    public void setCorreo(String correo) { Correo = correo; }
}
