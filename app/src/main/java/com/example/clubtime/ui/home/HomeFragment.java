package com.example.clubtime.ui.home;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clubtime.ConexionDB;
import com.example.clubtime.GlobalClass;
import com.example.clubtime.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class HomeFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private HomeViewModel homeViewModel;
    RecyclerView rvUsuario;
    ConexionDB conexionDB;
    GlobalClass gc;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton faAgregarAdmi;
    FloatingActionButton faEliminarAdmi;
    View root;

    //Elementos del Dialogo
    Dialog dialogo1;
    Button btAgregar;
    Button btEliminar;
    EditText etNombreUsuario;
    EditText etContraseña;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        conexionDB = new ConexionDB();
        gc = (GlobalClass) getActivity().getApplicationContext();

        floatingActionMenu = root.findViewById(R.id.floatingActionMenu);
        faAgregarAdmi = root.findViewById(R.id.btAgregarAdmi);
        faEliminarAdmi = root.findViewById(R.id.btEliminarAdmi);
        rvUsuario = root.findViewById(R.id.rvAdmon);

        conexionDB.getAdmonClub(gc.getActive_club(), rvUsuario, root.getContext());

        faAgregarAdmi.setOnClickListener(this);
        faEliminarAdmi.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btAgregarAdmi){
            MostrarAgregarAdmi();
            floatingActionMenu.close(true);
        }
        else if(v.getId() == R.id.btEliminarAdmi){
            MostrarEliminarAdmi();
            floatingActionMenu.close(true);
        }
    }

    public void MostrarAgregarAdmi(){
        dialogo1 = new Dialog(root.getContext());
        dialogo1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo1.setCancelable(true);
        dialogo1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogo1.setContentView(R.layout.dialog_agregar_admon);

        etNombreUsuario = dialogo1.findViewById(R.id.etNombreUsuario);
        etContraseña = dialogo1.findViewById(R.id.etContraseña);
        btAgregar = dialogo1.findViewById(R.id.btAgregar);

        etNombreUsuario.addTextChangedListener(this);
        etContraseña.addTextChangedListener(this);

        btAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNombreUsuario.getText().toString().equals("") || etContraseña.getText().toString().equals("")){
                    if(etNombreUsuario.getText().toString().equals("")) etNombreUsuario.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                    if(etContraseña.getText().toString().equals("")) etContraseña.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                    Toast.makeText(root.getContext(),"Los campos no pueden estar vacios" ,Toast.LENGTH_LONG).show();
                }
                else{
                    if(!etContraseña.getText().toString().equals(gc.getActive_club().getContra())){
                        Toast.makeText(root.getContext(),"La contraseña ingresada no es correcta." ,Toast.LENGTH_LONG).show();
                    }
                    else {
                        conexionDB.AgregarAdmiClub(etNombreUsuario.getText().toString(), gc.getActive_club(), rvUsuario, root.getContext());
                        dialogo1.cancel();
                    }
                }
            }
        });
        dialogo1.show();
    }

    public void MostrarEliminarAdmi(){
        dialogo1 = new Dialog(root.getContext());
        dialogo1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo1.setCancelable(true);
        dialogo1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialogo1.setContentView(R.layout.dialog_eliminar_admon);

        etNombreUsuario = dialogo1.findViewById(R.id.etNombreUsuario);
        etContraseña = dialogo1.findViewById(R.id.etContraseña);
        btEliminar = dialogo1.findViewById(R.id.btEliminar);

        etNombreUsuario.addTextChangedListener(this);
        etContraseña.addTextChangedListener(this);

        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNombreUsuario.getText().toString().equals("") || etContraseña.getText().toString().equals("")){
                    if(etNombreUsuario.getText().toString().equals("")) etNombreUsuario.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                    if(etContraseña.getText().toString().equals("")) etContraseña.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
                    Toast.makeText(root.getContext(),"Los campos no pueden estar vacios" ,Toast.LENGTH_LONG).show();
                }
                else{
                    if(!etContraseña.getText().toString().equals(gc.getActive_club().getContra())){
                        Toast.makeText(root.getContext(),"La contraseña ingresada no es correcta." ,Toast.LENGTH_LONG).show();
                    }
                    else {
                        conexionDB.EliminarAdmiClub(etNombreUsuario.getText().toString(), gc.getActive_club(), rvUsuario, root.getContext());
                        dialogo1.cancel();
                    }
                }

            }
        });
        dialogo1.show();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!etNombreUsuario.getText().toString().equals("")) etNombreUsuario.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
        if(!etContraseña.getText().toString().equals(""))    etContraseña.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}