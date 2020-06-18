package com.example.clubtime.ui.ingresarcodigo;

import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clubtime.Club;
import com.example.clubtime.GlobalClass;
import com.example.clubtime.R;
import com.example.clubtime.ConexionDB;

import java.util.HashMap;
import java.util.Map;

public class IngresarCodigoFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private IngresarCodigoViewModel ingresarCodigoViewModel;
    GlobalClass gc;
    ConexionDB conexionDB;
    View root;

    TextView tvClase;
    TextView tvEsperar;
    TextView tvCodigo;
    EditText etCodigo;
    Button btCodigo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ingresarCodigoViewModel = ViewModelProviders.of(this).get(IngresarCodigoViewModel.class);
        root = inflater.inflate(R.layout.fragment_ingresar_codigo, container, false);

        conexionDB = new ConexionDB();
        gc = (GlobalClass) getActivity().getApplicationContext();

        conexionDB.getClubId(gc.getActive_club(), gc.getActive_user(), gc, root);

        tvClase = root.findViewById(R.id.tvClase);
        tvEsperar = root.findViewById(R.id.tvEsperar);
        tvCodigo = root.findViewById(R.id.tvCodigo);
        etCodigo = root.findViewById(R.id.etCodigo);
        btCodigo = root.findViewById(R.id.btCodigo);

        etCodigo.addTextChangedListener(this);
        btCodigo.setOnClickListener(this);

        if(gc.getActive_club().getEstado().equals("000000")){
            String text = "Clase No Iniciada";
            tvClase.setText(text);
            tvCodigo.setVisibility(View.INVISIBLE);
            etCodigo.setVisibility(View.INVISIBLE);
            btCodigo.setVisibility(View.INVISIBLE);
        }
        else{
            String text = "Clase Iniciada";
            tvClase.setText(text);
            tvEsperar.setVisibility(View.INVISIBLE);
        }

        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btCodigo){
            if (etCodigo.getText().toString().equals("")) {
                etCodigo.getBackground().setColorFilter(Color.parseColor("#7f7f7f"), PorterDuff.Mode.DARKEN);
            }
            else {
                conexionDB.PaseLista(gc.getActive_club(), gc.getActive_user(), etCodigo.getText().toString(), root);
            }
        }
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        etCodigo.getBackground().setColorFilter(Color.parseColor("#FFB8DDED"), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}