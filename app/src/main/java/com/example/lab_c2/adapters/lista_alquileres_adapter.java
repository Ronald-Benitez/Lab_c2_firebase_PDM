package com.example.lab_c2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_c2.R;
import com.example.lab_c2.RegistroAlquiler;
import com.example.lab_c2.entidades.alquilerLista;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class lista_alquileres_adapter extends RecyclerView.Adapter<lista_alquileres_adapter.ViewHolder> {
    ArrayList<alquilerLista> lista;

    public lista_alquileres_adapter (ArrayList<alquilerLista> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_alquiler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.nombreV.setText(lista.get(position).getNombreV());
            holder.nombreC.setText(lista.get(position).getNombreC());
            holder.id.setText(lista.get(position).getIdA());
        }catch (Exception e){
            e.toString();
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreC,nombreV,id;
        FloatingActionButton editar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreC = itemView.findViewById(R.id.nombreC);
            nombreV = itemView.findViewById(R.id.nombreV);
            id = itemView.findViewById(R.id.idAlquiler);
            editar = itemView.findViewById(R.id.editarAlquilerFloat);

            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RegistroAlquiler.class);
                    intent.putExtra("ID",lista.get(getAdapterPosition()).getIdA());
                    context.startActivity(intent);
                }
            });
        }
    }
}
