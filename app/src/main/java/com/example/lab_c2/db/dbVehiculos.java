package com.example.lab_c2.db;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    public long createVehiculo(String placa, String tipo, String estado,String nombre){
        final long[] id = {0};

        vehiculo ve = new vehiculo();
        ve.setId(UUID.randomUUID().toString());
        ve.setPlaca(placa);
        ve.setTipo(tipo);
        ve.setEstado(estado);
        ve.setNombre(nombre);

        db.collection("vehiculos").document(ve.getId()).set(ve).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                id[0] =2;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                id[0] =0;
            }
        });

        return id[0];
    }

    public ArrayList<String> spinnerVehiculos(){
        ArrayList<String> lista = new ArrayList<>();
        String ve = null;

        return  lista;
    }

    public ArrayList<vehiculo> readVehiculos (){
        ArrayList<vehiculo> lista = new ArrayList<>();

        db.collection("vehiculos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                vehiculo ve = new vehiculo();
                                ve.setId(document.getString("id"));
                                ve.setPlaca(document.getString("placa"));
                                ve.setTipo(document.getString("tipo"));
                                ve.setEstado(document.getString("estado"));
                                ve.setNombre(document.getString("nombre"));
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                Log.d("a",ve.getNombre());
                                //
                                lista.add(ve);
                                Log.d("b",lista.get(0).getNombre());
                            }
                        }
                    }
                });

        return  lista;
    }

    public vehiculo findVehiculo(String clave, String valor){
        vehiculo ve = null;

        return  ve;
    }

    public boolean updateVehiculo(String id,String placa, String tipo, String estado,String nombre){
        boolean updated = false;

        return updated;
    }

    public boolean updateEstadoVehiculo(String id, String estado){
        boolean updated = false;



        return updated;
    }

    public boolean deleteVehiculo(String id){
        boolean removed = false;


        return removed;
    }

}
