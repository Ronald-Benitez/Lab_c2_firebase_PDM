package com.example.lab_c2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lab_c2.adapters.lista_vehiculos_adapter;
import com.example.lab_c2.db.dbVehiculos;
import com.example.lab_c2.entidades.vehiculo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Vehiculos extends AppCompatActivity {

    public Button newVehiculo,findVehiculo;
    Spinner claveBusqueda;
    EditText valorBusqueda;
    public RecyclerView lista;
    public dbVehiculos db = new dbVehiculos(Vehiculos.this);
    ArrayList<vehiculo> listaVehiculos;
    FirebaseFirestore dbF=FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);

        findVehiculo = findViewById(R.id.findButton);
        claveBusqueda = findViewById(R.id.claveBusqueda);
        valorBusqueda = findViewById(R.id.valorBusqueda);

        lista = findViewById(R.id.listaVehiculos);
        lista.setLayoutManager(new LinearLayoutManager(this));

        newVehiculo = findViewById(R.id.newVehiculo);


        firebaseGetItem();

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,R.array.spinnerClaveVehiculo, android.R.layout.simple_spinner_dropdown_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item);
        claveBusqueda.setAdapter(adapterSpinner);


        newVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vehiculos.this,verVehiculo.class);
                startActivity(intent);
            }
        });

        findVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clave = claveBusqueda.getSelectedItem().toString();
                String valor = valorBusqueda.getText().toString();

                dbF.collection("vehiculos").whereEqualTo(clave,valor).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Intent intent = new Intent(Vehiculos.this,verVehiculo.class);
                        intent.putExtra("ID",queryDocumentSnapshots.getDocuments().get(0).getId());
                        startActivity(intent);
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Vehiculos.this, "Vehiculo no encontrado", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
    public void firebaseGetItem(){
        listaVehiculos = new ArrayList<>();
        dbF.collection("vehiculos")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                vehiculo ve = document.toObject(vehiculo.class);
                                listaVehiculos.add(ve);
                            }
                            lista_vehiculos_adapter adapter = new lista_vehiculos_adapter(listaVehiculos);
                            lista.setAdapter(adapter);
                        }
                    }
                });

    }
}