package com.example.clubtime.ui.neutral;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.clubtime.GlobalClass;
import com.example.clubtime.InicioAlumno;
import com.example.clubtime.R;
import com.example.clubtime.Usuario;
import com.example.clubtime.ui.home.HomeFragment;
import com.example.clubtime.ui.principalalumno.PrincipalAlumnoFragment;

public class NeutralFragment extends Fragment {

    private NeutralViewModel neutralViewModel;
    GlobalClass globalClass;
    Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        neutralViewModel = ViewModelProviders.of(this).get(NeutralViewModel.class);
        View root = inflater.inflate(R.layout.fragment_neutral, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();
        usuario = globalClass.getActive_user();

        if(usuario.getTipo() == 1 ) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, new HomeFragment());
            transaction.commit();
        }
        else{
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, new PrincipalAlumnoFragment());
            transaction.commit();
    }

        return root;
    }
}
