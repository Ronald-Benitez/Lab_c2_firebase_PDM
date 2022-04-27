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
import com.example.lab_c2.entidades.Clientes;
import com.example.lab_c2.verCliente;

import java.util.ArrayList;

public class lista_clientes_adapter extends RecyclerView.Adapter<lista_clientes_adapter.clienteViewHolder> {
    ArrayList<Clientes> listaClientes;

    public lista_clientes_adapter(ArrayList<Clientes> listaClientes){
        this.listaClientes =listaClientes;
    }
    @NonNull
    @Override
    public clienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_clientes,null,false);
        return new clienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull clienteViewHolder holder, int position) {
        holder.viewNombre.setText(listaClientes.get(position).getNombre());
        holder.viewId.setText(String.valueOf(listaClientes.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class clienteViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombre, viewId;

        public clienteViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.nombreCtxt);
            viewId = itemView.findViewById(R.id.idCtxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, verCliente.class);
                    intent.putExtra("id",listaClientes.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

        }
    }
}
