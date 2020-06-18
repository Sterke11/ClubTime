package com.example.clubtime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterDataHorasAlumno extends RecyclerView.Adapter<AdapterDataHorasAlumno.ViewHolderData> implements View.OnClickListener {

    ArrayList<String> listaNombres;
    ArrayList<String> listaHoras;
    private View.OnClickListener listener;

    public AdapterDataHorasAlumno(ArrayList<String> listaNombres, ArrayList<String> listaHoras){
        this.listaNombres=listaNombres;
        this.listaHoras = listaHoras;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(Utilidades.CON_INFO==Utilidades.status){
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlist_horas_alumno,viewGroup,false);
            view.setOnClickListener(this);
            return new ViewHolderData(view);
        }else {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_void,viewGroup,false);
            return new ViewHolderData(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData viewHolderData, int i) {
        if(Utilidades.CON_INFO==Utilidades.status) {
            viewHolderData.tv_Nombre.setText(listaNombres.get(i));
            viewHolderData.tv_Horas.setText(listaHoras.get(i));
//          viewHolderData.iv_ImagenClubList.setImageResource(listaDatos.get(i).getFoto());
        }else{
            viewHolderData.tv_SinResultados.setText("Sin Usuarios");
        }
    }

    @Override
    public int getItemCount() {
        return listaNombres.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {

        if(listener!=null){
            listener.onClick(v);
        }

    }

    public class ViewHolderData extends RecyclerView.ViewHolder {
        TextView tv_Nombre;
        TextView tv_Horas;
        TextView tv_SinResultados;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);

            if(Utilidades.CON_INFO==Utilidades.status) {
                tv_Nombre = (TextView) itemView.findViewById(R.id.tv_Nombre);
                tv_Horas = (TextView) itemView.findViewById(R.id.tv_Horas);
            }else{
                tv_SinResultados=(TextView) itemView.findViewById(R.id.tv_SinResultados);
            }

        }


    }
}
