package com.example.lab_c2.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lab_c2.Clientes_Activity;
import com.example.lab_c2.entidades.Clientes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class dbClientes{

    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public dbClientes(@Nullable Context context) {
        super();
        this.context = context;

    }

    public void insertarCliente(String nombre){
        Clientes cliente = new Clientes(UUID.randomUUID().toString(),nombre);
        db.collection("clientes").document(cliente.getId()).set(cliente).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context,"Cliente insertado",Toast.LENGTH_SHORT).show();
            }

            public void onfailure(Exception e){
                Toast.makeText(context,"Error al insertar",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void editarCliente(String id,String nombre){
            Clientes cliente = new Clientes(id,nombre);
            db.collection("clientes").document(id).set(cliente).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context,"Cliente editado",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Error al editar",Toast.LENGTH_SHORT).show();
                }
            });

    }

    public void eliminarClinete(String id){
        db.collection("clientes").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context,"Cliente eliminado",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Clientes_Activity.class);
                context.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Error al eliminar",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
