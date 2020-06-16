package com.example.clubtime.ui.principalalumno;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.example.clubtime.Club;
import com.example.clubtime.GlobalClass;
import com.example.clubtime.R;
import com.example.clubtime.ConexionDB;

public class PrincipalAlumnoFragment extends Fragment {

    private PrincipalAlumnoViewModel principalAlumnoViewModel;
    GlobalClass gc;
    ConexionDB conexionDB;
    RecyclerView rvAdmon;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        principalAlumnoViewModel = ViewModelProviders.of(this).get(PrincipalAlumnoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_principal_alumno, container, false);

        rvAdmon = root.findViewById(R.id.rvAdmonUsuario);

        gc = (GlobalClass) getActivity().getApplicationContext();
        conexionDB = new ConexionDB();

        conexionDB.getAdmonClub(gc.getActive_club(), rvAdmon, root.getContext());



        return root;
    }

}
