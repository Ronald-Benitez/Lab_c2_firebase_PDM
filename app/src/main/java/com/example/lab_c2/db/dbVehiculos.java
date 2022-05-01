package com.example.lab_c2.db;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_c2.Vehiculos;
import com.example.lab_c2.entidades.vehiculo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.UUID;

public class dbVehiculos {
    Context context;
    //private FirebaseDatabase db;
    //private DatabaseReference dbRef;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public dbVehiculos(@Nullable Context context) {
        super();
        this.context = context;
        /*FirebaseApp.initializeApp(context);
        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference();*/

    }

    public void createVehiculo(String placa, String tipo, String estado,String nombre){
        vehiculo ve = new vehiculo();
        ve.setId(UUID.randomUUID().toString());
        ve.setPlaca(placa);
        ve.setTipo(tipo);
        ve.setEstado(estado);
        ve.setNombre(nombre);

        db.collection("vehiculos").document(ve.getId()).set(ve).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Vehiculo creado", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al crear vehiculo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateVehiculo(vehiculo ve){
        db.collection("vehiculos").document(ve.getId()).set(ve);
    }

    public void updateVehiculo(String id,String placa, String tipo, String estado,String nombre){
        vehiculo ve = new vehiculo(id,placa,tipo,estado,nombre);

        db.collection("vehiculos").document(id).set(ve).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Vehiculo actualizado", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al actualizar vehiculo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deleteVehiculo(String id){
        db.collection("vehiculos").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Vehiculo eliminado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Vehiculos.class);
                context.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error al eliminar vehiculo", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
