package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lab_c2.adapters.lista_alquileres_adapter;
import com.example.lab_c2.db.dbAlquileres;
import com.example.lab_c2.entidades.alquilerLista;

import java.util.ArrayList;

public class adminAlquiler extends AppCompatActivity {
    RecyclerView listView;
    dbAlquileres alquileresDB = new dbAlquileres(adminAlquiler.this);
    ArrayList<alquilerLista> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_alquiler);
        listView = findViewById(R.id.listaAlquileres);
        listView.setLayoutManager(new LinearLayoutManager(this));

        lista_alquileres_adapter adapter = new lista_alquileres_adapter(alquileresDB.readListAlquiler());
        listView.setAdapter(adapter);
    }
}