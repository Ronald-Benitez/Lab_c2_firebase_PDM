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
    Button adminVehiculo;
    Button adminCliemte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        //INICIALIZACION DE BOTONES

        adminVehiculo = findViewById(R.id.adminVehiculos);
        adminCliemte = findViewById(R.id.adminClientes);
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
    }

    public void createDB(){
        dbHelper DBhelper = new dbHelper(MainActivity.this);
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        if(db != null){
            Toast.makeText(MainActivity.this, "Base de datos creada", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Error la crear la base de datos", Toast.LENGTH_SHORT).show();

        }
    }
}