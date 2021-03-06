package com.example.clubtime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterDataAlumnoCorreo extends RecyclerView.Adapter<AdapterDataAlumnoCorreo.ViewHolderData> implements View.OnClickListener {

    ArrayList<Usuario> listaDatos;
    private View.OnClickListener listener;

    public AdapterDataAlumnoCorreo(ArrayList<Usuario> listaDatos){
        this.listaDatos=listaDatos;
    }


    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(Utilidades.CON_INFO==Utilidades.status){
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlist_alumno_correo,viewGroup,false);
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
            String Nombre = listaDatos.get(i).getNombre() + " " + listaDatos.get(i).getApPaterno() + " " + listaDatos.get(i).getApMaterno();
            viewHolderData.tv_Nombre.setText(Nombre);
            viewHolderData.tv_Correo.setText(listaDatos.get(i).getCorreo());
            viewHolderData.swEnviar.setChecked(false);
        }else{
            viewHolderData.tv_SinResultados.setText("Sin Usuarios");
        }
    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
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
        TextView tv_Correo;
        TextView tv_Nombre;
        TextView tv_Boleta;
        TextView tv_SinResultados;
        Switch swEnviar;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);

            if(Utilidades.CON_INFO==Utilidades.status) {
                tv_Correo = (TextView) itemView.findViewById(R.id.tv_Correo);
                tv_Nombre = (TextView) itemView.findViewById(R.id.tv_Nombre);
                swEnviar = (Switch) itemView.findViewById(R.id.swEnviar);
            }else{
                tv_SinResultados=(TextView) itemView.findViewById(R.id.tv_SinResultados);
            }

        }


    }
}
