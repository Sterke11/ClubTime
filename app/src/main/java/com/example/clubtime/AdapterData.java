package com.example.clubtime;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolderData> implements View.OnClickListener {

    ArrayList<Club> listaDatos;
    private View.OnClickListener listener;

    public AdapterData(ArrayList<Club> listaDatos){
        this.listaDatos=listaDatos;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(Utilidades.CON_INFO==Utilidades.status){
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlist,null,false);
            view.setOnClickListener(this);
            return new ViewHolderData(view);
        }else {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_void,null,false);
            return new ViewHolderData(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData viewHolderData, int i) {
        if(Utilidades.CON_INFO==Utilidades.status) {
            viewHolderData.tv_NombreClubList.setText(listaDatos.get(i).getNombre());
            viewHolderData.tv_InfoList.setText(listaDatos.get(i).getFechAlta());
//        viewHolderData.iv_ImagenClubList.setImageResource(listaDatos.get(i).getFoto());
        }else{
            viewHolderData.tv_SinResultados.setText("Sin resultados");
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
        ImageView iv_ImagenClubList;
        TextView tv_NombreClubList;
        TextView tv_InfoList;
        TextView tv_SinResultados;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);

            if(Utilidades.CON_INFO==Utilidades.status) {
                iv_ImagenClubList = (ImageView) itemView.findViewById(R.id.iv_ImagenClubList);
                tv_NombreClubList = (TextView) itemView.findViewById(R.id.tv_NombreClubList);
                tv_InfoList = (TextView) itemView.findViewById(R.id.tv_InfoList);
            }else{
                tv_SinResultados=(TextView) itemView.findViewById(R.id.tv_SinResultados);
            }

        }


    }
}
