package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab_c2.db.dbHelper;

public class MainActivity extends AppCompatActivity {
    Button adminVehiculo, registroAlquiler,adminAlquiler;
    Button adminCliemte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIALIZACION DE BOTONES

        adminVehiculo = findViewById(R.id.adminVehiculos);
        adminCliemte = findViewById(R.id.adminClientes);
        registroAlquiler = findViewById(R.id.registroAlquiler);
        adminAlquiler = findViewById(R.id.adminAlquileres);
        //EVENTOS DE NOTONCES

        //EVENTO DE VEHICULOS
        adminVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Vehiculos.class);
                startActivity(intent);
            }
        });

        //EVENTO DE CLIENTES
        adminCliemte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Clientes_Activity.class);
                startActivity(intent);
            }
        });

        //EVENTO DE RENTA
        registroAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegistroAlquiler.class);
                startActivity(intent);
            }
        });

        //EVENTO DE ADMINISTRACION DE ALQUILERES
        adminAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,adminAlquiler.class);
                startActivity(intent);
            }
        });
    }

}