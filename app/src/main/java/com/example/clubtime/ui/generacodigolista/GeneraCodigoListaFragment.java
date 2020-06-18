package com.example.clubtime.ui.generacodigolista;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clubtime.Club;
import com.example.clubtime.GlobalClass;
import com.example.clubtime.R;
import com.example.clubtime.ConexionDB;

import java.util.HashMap;
import java.util.Map;

public class GeneraCodigoListaFragment extends Fragment implements View.OnClickListener {

    private GeneraCodigoListaViewModel generaCodigoListaViewModel;
    GlobalClass gc;
    ConexionDB conexionDB;
    TextView tv_Clase;
    TextView tv_codigoGenerado;
    Button bt_generaCodigo;
    View root;

    //Generar el código
    Map<Integer,String> map =  new HashMap<Integer, String>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        generaCodigoListaViewModel = ViewModelProviders.of(this).get(GeneraCodigoListaViewModel.class);
        root = inflater.inflate(R.layout.fragment_genera_codigo_lista, container, false);

        LlenarDiccionario();

        tv_Clase = root.findViewById(R.id.tv_Clase);
        tv_codigoGenerado = root.findViewById(R.id.tv_codigoGenerado);
        bt_generaCodigo = root.findViewById(R.id.bt_generaCodigo);
        bt_generaCodigo.setOnClickListener(this);

        gc = (GlobalClass) getActivity().getApplicationContext();
        conexionDB = new ConexionDB();

        if(gc.getActive_club().getEstado().equals("000000")) {
            tv_Clase.setVisibility(View.INVISIBLE);
            String text = "Aun no se ha generado un codigo para la clase";
            tv_codigoGenerado.setText(text);
            text = "Generar Codigo";
            bt_generaCodigo.setText(text);
        }
        else{
            tv_Clase.setVisibility(View.VISIBLE);
            String text = "El codigo generado para la sesión es:\n" + gc.getActive_club().getEstado();
            tv_codigoGenerado.setText(text);
            text = "Terminar clase";
            bt_generaCodigo.setText(text);
        }

        return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_generaCodigo) {

            if(gc.getActive_club().getEstado().equals("000000")) {

                String Codigo = "";
                for (int i = 0; i < 6; i++) {
                    Codigo += map.get((int) (Math.random() * 61));
                }
                //tv_codigoGenerado.setText(Codigo);
                conexionDB.CambiarEstadoClub(gc.getActive_club(), Codigo, root);

                //Toast.makeText(root.getContext(), "Estado: " + gc.getActive_club().getEstado(), Toast.LENGTH_LONG).show();
            }
            else{
                conexionDB.CambiarEstadoClub(gc.getActive_club(), "000000", root);
            }
        }
    }
    public void LlenarDiccionario(){
        map.put(0, "0");
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        map.put(4, "4");
        map.put(5, "5");
        map.put(6, "6");
        map.put(7, "7");
        map.put(8, "8");
        map.put(9, "9");
        map.put(10, "a");
        map.put(11, "b");
        map.put(12, "c");
        map.put(13, "d");
        map.put(14, "e");
        map.put(15, "f");
        map.put(16, "g");
        map.put(17, "h");
        map.put(18, "i");
        map.put(19, "j");
        map.put(20, "k");
        map.put(21, "l");
        map.put(22, "m");
        map.put(23, "n");
        map.put(24, "o");
        map.put(25, "p");
        map.put(26, "q");
        map.put(27, "r");
        map.put(28, "s");
        map.put(29, "t");
        map.put(30, "u");
        map.put(31, "v");
        map.put(32, "w");
        map.put(33, "x");
        map.put(34, "y");
        map.put(35, "z");
        map.put(36, "A");
        map.put(37, "B");
        map.put(38, "C");
        map.put(39, "D");
        map.put(40, "E");
        map.put(41, "F");
        map.put(42, "G");
        map.put(43, "H");
        map.put(44, "I");
        map.put(45, "J");
        map.put(46, "K");
        map.put(47, "L");
        map.put(48, "M");
        map.put(49, "N");
        map.put(50, "O");
        map.put(51, "P");
        map.put(52, "Q");
        map.put(53, "R");
        map.put(54, "S");
        map.put(55, "T");
        map.put(56, "U");
        map.put(57, "V");
        map.put(58, "W");
        map.put(59, "X");
        map.put(60, "Y");
        map.put(61, "Z");



    }
}
