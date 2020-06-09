package com.example.clubtime;

import android.app.Application;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Club  extends Application implements Serializable {

    private int ID;
    private String Alias;
    private String Nombre;
    private String Contra;
    private String UserAlta;
    private String FechAlta;
    private String UserMod;
    private String FechMod;
    private int Foto;

    public Club() {}

    public Club(@NotNull JSONObject jsonObject) throws JSONException {
        setID(jsonObject.getInt("id_club"));
        setAlias(jsonObject.getString("alias_club"));
        setNombre(jsonObject.getString("nombre_club"));
        setContra(jsonObject.getString("contraseña"));
        setUserAlta(jsonObject.getString("user_alta"));
        setFechAlta(jsonObject.getString("fecha_alta"));
        setUserMod(jsonObject.getString("user_mod"));
        setFechMod(jsonObject.getString("fecha_mod"));
        setFoto(-1);
    }

    public Club(int ID, String alias, String nombre, String contra, String userAlta, String fechAlta, String userMod, String fechMod, int foto) {
        this.ID = ID;
        Alias = alias;
        Nombre = nombre;
        Contra = contra;
        UserAlta = userAlta;
        FechAlta = fechAlta;
        UserMod = userMod;
        FechMod = fechMod;
        Foto = foto;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public String getAlias() { return Alias; }

    public void setAlias(String alias) { Alias = alias; }

    public String getNombre() { return Nombre; }

    public void setNombre(String nombre) { Nombre = nombre; }

    public String getContra() { return Contra; }

    public void setContra(String contra) { Contra = contra; }

    public String getUserAlta() { return UserAlta; }

    public void setUserAlta(String userAlta) { UserAlta = userAlta; }

    public String getFechAlta() { return FechAlta; }

    public void setFechAlta(String fechAlta) { FechAlta = fechAlta; }

    public String getUserMod() { return UserMod; }

    public void setUserMod(String userMod) { UserMod = userMod; }

    public String getFechMod() { return FechMod; }

    public void setFechMod(String fechMod) { FechMod = fechMod; }

    public int getFoto() { return Foto; }

    public void setFoto(int foto) { Foto = foto; }
}
