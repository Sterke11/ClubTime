package com.example.clubtime.ui.enviarcorreo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.clubtime.ConexionDB;
import com.example.clubtime.GlobalClass;
import com.example.clubtime.R;
import com.example.clubtime.ui.slideshow.SlideshowViewModel;

public class EnviarCorreoFragment extends Fragment {

    private EnviarCorreoViewModel enviarCorreoViewModel;
    ConexionDB conexionDB;
    EditText etmContenido;
    GlobalClass gc;
    RecyclerView rvDestinatarios;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        enviarCorreoViewModel = ViewModelProviders.of(this).get(EnviarCorreoViewModel.class);
        root = inflater.inflate(R.layout.fragment_enviar_correo, container, false);

        conexionDB = new ConexionDB();
        gc = (GlobalClass) getActivity().getApplicationContext();
        rvDestinatarios = root.findViewById(R.id.rvDestinatarios);
        etmContenido = root.findViewById(R.id.etmContenido);

        etmContenido.setMovementMethod(new ScrollingMovementMethod());

        conexionDB.getUsuariosClubCorreo(gc.getActive_club(), rvDestinatarios, root.getContext());



        return root;
    }
}
