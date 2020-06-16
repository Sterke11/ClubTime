package com.example.clubtime.ui.listaalumnosdealumno;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clubtime.ConexionDB;
import com.example.clubtime.GlobalClass;
import com.example.clubtime.R;
import com.example.clubtime.ui.principalalumno.PrincipalAlumnoViewModel;


public class ListaAlumnosdeAlumnoFragment extends Fragment {

    private ListaAlumnosdeAlumnoViewModel listaAlumnosdeAlumnoViewModel;
    GlobalClass gc;
    ConexionDB conexionDB;
    RecyclerView rvAlumnoUsuario;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listaAlumnosdeAlumnoViewModel = ViewModelProviders.of(this).get(ListaAlumnosdeAlumnoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list_alumnos_de_alumno, container, false);

        gc = (GlobalClass) getActivity().getApplicationContext();
        conexionDB = new ConexionDB();
        rvAlumnoUsuario = root.findViewById(R.id.rvAlumnoUsuario);

        conexionDB.getUsuariosClub(gc.getActive_club(),rvAlumnoUsuario, root.getContext());

        return root;
    }
}
