package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab_c2.adapters.lista_alquileres_adapter;
import com.example.lab_c2.adapters.lista_clientes_adapter;
import com.example.lab_c2.db.dbAlquileres;
import com.example.lab_c2.entidades.Alquiler;
import com.example.lab_c2.entidades.Clientes;
import com.example.lab_c2.entidades.alquilerLista;
import com.example.lab_c2.entidades.vehiculo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class adminAlquiler extends AppCompatActivity {
    RecyclerView listView;

    dbAlquileres alquileresDB = new dbAlquileres(adminAlquiler.this);
    ArrayList<alquilerLista> lista;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_alquiler);
        listView = findViewById(R.id.listaAlquileres);

        listView.setLayoutManager(new LinearLayoutManager(this));

        getData();

    }

    public void getData(){
        lista = new ArrayList<>();
        db.collection("alquileres").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            alquilerLista c = document.toObject(alquilerLista.class);
                            lista.add(c);
                        }
                        lista_alquileres_adapter adapter = new lista_alquileres_adapter(lista);
                        listView.setAdapter(adapter);
                    }
                }
            }
        });
    }
}