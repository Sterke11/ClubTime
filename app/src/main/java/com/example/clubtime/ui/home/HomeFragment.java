package com.example.clubtime.ui.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.example.clubtime.ConexionDB;
import com.example.clubtime.GlobalClass;
import com.example.clubtime.R;
import com.github.clans.fab.FloatingActionButton;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView rvUsuario;
    ConexionDB conexionDB;
    GlobalClass gc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        conexionDB = new ConexionDB();
        gc = (GlobalClass) getActivity().getApplicationContext();

        rvUsuario = root.findViewById(R.id.rvAdmon);
        conexionDB.getAdmonClub(gc.getActive_club(), rvUsuario, root.getContext());

        return root;
    }
}