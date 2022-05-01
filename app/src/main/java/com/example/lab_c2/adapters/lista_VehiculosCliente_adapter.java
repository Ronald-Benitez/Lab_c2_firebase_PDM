package com.example.lab_c2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_c2.R;
import com.example.lab_c2.entidades.vehiculosCliente;

import java.util.ArrayList;

public class lista_VehiculosCliente_adapter extends RecyclerView.Adapter<lista_VehiculosCliente_adapter.vehiculosClienteViewHolder> {
    ArrayList<vehiculosCliente> listaCCvehiculos;

    public lista_VehiculosCliente_adapter( ArrayList<vehiculosCliente> listaCCvehiculos){
        this.listaCCvehiculos = listaCCvehiculos;
    }
    @NonNull
    @Override
    public vehiculosClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_vehiculoscliente,null,false);
        return new vehiculosClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vehiculosClienteViewHolder holder, int position) {
        holder.vNombre.setText(listaCCvehiculos.get(position).getNombreV());
        holder.vInicio.setText(listaCCvehiculos.get(position).getFechaInicio());
        holder.vFinal.setText(listaCCvehiculos.get(position).getFechaFin());
        holder.vTiempo.setText(listaCCvehiculos.get(position).getTiempoAlquiler() + "Dias");
        holder.vPrecio.setText("$ "+ listaCCvehiculos.get(position).getPrecioAlquiler());

    }

    @Override
    public int getItemCount() {
        return listaCCvehiculos.size();
    }

    public class vehiculosClienteViewHolder extends RecyclerView.ViewHolder {
        TextView vNombre,vInicio,vFinal,vTiempo,vPrecio;


        public vehiculosClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            vNombre = itemView.findViewById(R.id.CCNombre);
            vInicio = itemView.findViewById(R.id.CCinicioAlquiler);
            vFinal = itemView.findViewById(R.id.CCfinalAlquilr);
            vTiempo = itemView.findViewById(R.id.CCtiempo);
            vPrecio= itemView.findViewById(R.id.CCtrecio);

        }
    }
}