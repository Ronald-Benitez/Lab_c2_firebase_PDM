package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lab_c2.db.dbVehiculos;
import com.example.lab_c2.entidades.vehiculo;

public class verVehiculo extends AppCompatActivity {
    EditText nombreVehiculo,placaVehiculo;
    Spinner tipoVehiculo;
    Switch estadoVehiculo;
    Button vehiculoB,deleteVehiculo;
    public int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        setContentView(R.layout.activity_ver_vehiculo);
        nombreVehiculo = findViewById(R.id.nVehiculo);
        placaVehiculo = findViewById(R.id.pVehiculo);
        tipoVehiculo = findViewById(R.id.tVehiculo);
        estadoVehiculo = findViewById(R.id.eVehiculo);
        vehiculoB = findViewById(R.id.vehiculoBoton);
        deleteVehiculo = findViewById(R.id.deleteVehiculo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinnerTypes, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        tipoVehiculo.setAdapter(adapter);

        if(extras!=null){
            id = extras.getInt("ID");
            vehiculoB.setText("Actualizar");
            dbVehiculos db = new dbVehiculos(verVehiculo.this);
            vehiculo ve = db.findVehiculo("idV",String.valueOf(id));

            nombreVehiculo.setText(ve.getNombre());
            placaVehiculo.setText(ve.getPlaca());
            String tipos[] = {"Coche","Microbus","Furgoneta","Camión"};

            for(int i=0; i<4;i++){
                if(ve.getTipo()==tipos[i]){
                    tipoVehiculo.setSelection(i);
                }
            }
            estadoVehiculo.setText(ve.getEstado());
            deleteVehiculo.setVisibility(View.VISIBLE);

        }else{
            vehiculoB.setText("Registrar");
            estadoVehiculo.setText("Disponible");
        }

        vehiculoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbVehiculos db = new dbVehiculos(verVehiculo.this);

                if(id>0){
                    boolean updated = db.updateVehiculo(id,placaVehiculo.getText().toString(), tipoVehiculo.getSelectedItem().toString(), estadoVehiculo.getText().toString(), nombreVehiculo.getText().toString());

                    if(updated){
                        Toast.makeText(verVehiculo.this, "Vehiculo actualizado con éxito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(verVehiculo.this, "Error al actualizar el vehículo", Toast.LENGTH_SHORT).show();
                    }
                }else {

                    long id = db.createVehiculo(placaVehiculo.getText().toString(), tipoVehiculo.getSelectedItem().toString(), estadoVehiculo.getText().toString(), nombreVehiculo.getText().toString());

                    if (id > 0) {
                        Toast.makeText(verVehiculo.this, "Vehiculo registrado con éxito", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } else {
                        Toast.makeText(verVehiculo.this, "Error al registrar el vehículo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        estadoVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(estadoVehiculo.isChecked()){
                    estadoVehiculo.setText("Alquilado");
                }else{
                    estadoVehiculo.setText("Disponible");
                }
            }
        });

        deleteVehiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbVehiculos db = new dbVehiculos(verVehiculo.this);

                boolean removed = db.deleteVehiculo(id);

                if(removed){
                    Toast.makeText(verVehiculo.this, "Vehículo eliminado exitosamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(verVehiculo.this,Vehiculos.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(verVehiculo.this, "Error al eliminar el vehiculo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limpiar(){
        nombreVehiculo.setText("");
        placaVehiculo.setText("");
        estadoVehiculo.callOnClick();
        tipoVehiculo.setSelection(1);
    }
}