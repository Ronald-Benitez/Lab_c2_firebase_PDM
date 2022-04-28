package com.example.lab_c2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_c2.R;
import com.example.lab_c2.entidades.vehiculo;
import com.example.lab_c2.verVehiculo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class lista_vehiculos_adapter extends RecyclerView.Adapter<lista_vehiculos_adapter.ViewHolderVehiculos> {
    ArrayList<vehiculo> listaVehiculos;

    public lista_vehiculos_adapter(ArrayList<vehiculo> listaVehiculos){
        this.listaVehiculos = listaVehiculos;
    }

    @NonNull
    @Override
    public ViewHolderVehiculos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_vehiculo,null,false);
        return new ViewHolderVehiculos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVehiculos holder, int position) {
        try {
            holder.nombreVehiculo.setText(listaVehiculos.get(position).getNombre());
            holder.idVehiculo.setText(String.valueOf(listaVehiculos.get(position).getId()));
            holder.placaVehiculo.setText(listaVehiculos.get(position).getPlaca());
            holder.tipoVehiculo.setText(listaVehiculos.get(position).getTipo());
            holder.estadoVehiculo.setText(listaVehiculos.get(position).getEstado());
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return listaVehiculos.size();
    }

    public class ViewHolderVehiculos extends RecyclerView.ViewHolder {
        TextView idVehiculo,nombreVehiculo,placaVehiculo,tipoVehiculo,estadoVehiculo;
        FloatingActionButton edit;

        public ViewHolderVehiculos(@NonNull View itemView) {
            super(itemView);

            idVehiculo = itemView.findViewById(R.id.idVehiculo);
            nombreVehiculo = itemView.findViewById(R.id.nombreVehiculo);
            placaVehiculo = itemView.findViewById(R.id.placaVehiculo);
            tipoVehiculo = itemView.findViewById(R.id.tipoVehiculo);
            estadoVehiculo = itemView.findViewById(R.id.estadoVehiculo);
            edit = itemView.findViewById(R.id.editarVehiculoFloat);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, verVehiculo.class);
                    intent.putExtra("ID",listaVehiculos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
