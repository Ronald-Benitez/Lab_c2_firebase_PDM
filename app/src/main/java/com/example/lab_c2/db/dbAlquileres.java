package com.example.lab_c2.db;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_c2.adminAlquiler;
import com.example.lab_c2.entidades.Alquiler;
import com.example.lab_c2.entidades.vehiculo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.UUID;

public class dbAlquileres {
    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public dbAlquileres(@Nullable Context context) {
        super();
        this.context=context;
    }

    public void createAlquiler(String fechaInicio, String fechaFin,String tiempoAlquiler,String precioAlquiler,String nombreC,String nombreV){
        Alquiler al = new Alquiler(UUID.randomUUID().toString(),fechaInicio,fechaFin,tiempoAlquiler,precioAlquiler,nombreC,nombreV);

        db.collection("alquileres").document(al.getIdA()).set(al).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context,"Alquiler creado",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Error al crear alquiler",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateAlquiler(String id,String fechaInicio, String fechaFin,String tiempoAlquiler,String precioAlquiler,String nombreC,String nombreV){
        Alquiler al = new Alquiler(id,fechaInicio,fechaFin,tiempoAlquiler,precioAlquiler,nombreC,nombreV);

        db.collection("alquileres").document(al.getIdA()).set(al).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context,"Alquiler actualizado",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Error al actualizar el alquiler",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteAlquiler(String id,String vehiculo){
        dbVehiculos dbv = new dbVehiculos(context);
        db.collection("alquileres").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Alquiler eliminado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, adminAlquiler.class);
                context.startActivity(intent);

                db.collection("vehiculos").whereEqualTo("nombre", vehiculo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        vehiculo ve = documentSnapshot.toObject(vehiculo.class);
                        ve.setEstado("Disponible");
                        dbv.updateVehiculo(ve);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Error al eliminar el alquiler",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
