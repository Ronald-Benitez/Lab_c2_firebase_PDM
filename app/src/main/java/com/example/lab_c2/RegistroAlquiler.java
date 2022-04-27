package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_c2.db.dbAlquileres;
import com.example.lab_c2.db.dbClientes;
import com.example.lab_c2.db.dbVehiculos;
import com.example.lab_c2.entidades.vehiculo;

import java.util.ArrayList;

public class RegistroAlquiler extends AppCompatActivity {
    Button registro;
    EditText fechaInicio, fechaFin, tiempoAlquiler;
    TextView precioAlquiler;
    Spinner idVehiculo,idCliente;

    ArrayList<String> listVehiculos,listClientes;
    dbVehiculos vehiculosDB = new dbVehiculos(this);
    dbClientes clientesDB = new dbClientes(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alquiler);

        registro = findViewById(R.id.registroAl);
        fechaInicio = findViewById(R.id.fechaInicio);
        fechaFin = findViewById(R.id.fechaFin);
        tiempoAlquiler = findViewById(R.id.tiempoAlquiler);
        precioAlquiler = findViewById(R.id.precioAlquiler);
        idVehiculo = findViewById(R.id.idVehiculo);
        idCliente = findViewById(R.id.idCliente);

        listVehiculos = vehiculosDB.spinnerVehiculos();
        listClientes = clientesDB.spinnerClientes();

        ArrayAdapter<String> adapterV = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listVehiculos);
        idVehiculo.setAdapter(adapterV);

        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listClientes);
        idCliente.setAdapter(adapterC);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbAlquileres db = new dbAlquileres(RegistroAlquiler.this);

                String idV = idVehiculo.getSelectedItem().toString().split("-")[0];
                String idC = idCliente.getSelectedItem().toString().split("-")[0];
                String fechaI = fechaInicio.getText().toString();
                String fechaF = fechaFin.getText().toString();
                String tiempoAl = tiempoAlquiler.getText().toString();
                String precioAl = precioAlquiler.getText().toString();

                boolean campoVacio = false;

                if(fechaI.isEmpty()){
                    campoVacio=true;
                }

                if(fechaF.isEmpty()){
                    campoVacio=true;
                }

                if(tiempoAl.isEmpty()){
                    campoVacio=true;
                }

                if(precioAl.isEmpty()){
                    campoVacio=true;
                }

                if(!campoVacio) {
                    long id = db.createAlquiler(fechaI, fechaF, tiempoAl, precioAl, idV, idC);

                    if (id > 0) {
                        Toast.makeText(RegistroAlquiler.this, "Alquiler registrado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegistroAlquiler.this, "Error al registrar alquiler", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegistroAlquiler.this, "No debe dejar campos vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tiempoAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(idVehiculo.getSelectedItem().toString().split("-")[0]);
                int tiempo = Integer.parseInt(tiempoAlquiler.getText().toString());

                if (tiempo != 0) {
                    dbVehiculos db = new dbVehiculos(RegistroAlquiler.this);
                    vehiculo datos = db.findVehiculo("idV", String.valueOf(id));
                    int precio = 0;

                    if (datos != null) {
                        String tipo = datos.getTipo();

                        switch (tipo) {
                            case "Coche":
                                precio = 65 * tiempo;
                                break;
                            case "Microbus":
                                precio = 65 * tiempo + 20;
                                break;
                            case "Furgoneta":
                                precio = 70 * tiempo;
                                break;
                            case "Camión":
                                precio = 75 * tiempo;
                                break;
                            default:
                                precio = 0;
                                break;
                        }

                    } else {
                        Toast.makeText(RegistroAlquiler.this, "Error al calcular el precio", Toast.LENGTH_SHORT).show();
                    }

                    precioAlquiler.setText(String.valueOf(precio));
                }
            }
        });

    }


}