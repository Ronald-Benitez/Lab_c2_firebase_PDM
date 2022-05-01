package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class verVehiculo extends AppCompatActivity {
    EditText nombreVehiculo,placaVehiculo;
    Spinner tipoVehiculo;
    Switch estadoVehiculo;
    Button vehiculoB,deleteVehiculo;
    public String id="";

    public FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    public dbVehiculos db=new dbVehiculos(this);

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
            id = extras.getString("ID");
            vehiculoB.setText("Actualizar");

            DocumentReference docRef = dbF.collection("vehiculos").document(id);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    vehiculo v = documentSnapshot.toObject(vehiculo.class);
                    nombreVehiculo.setText(v.getNombre());
                    placaVehiculo.setText(v.getPlaca());
                    tipoVehiculo.setSelection(adapter.getPosition(v.getTipo()));
                    estadoVehiculo.setText(v.getEstado());
                }
            });

            deleteVehiculo.setVisibility(View.VISIBLE);

        }else{
            vehiculoB.setText("Registrar");
            estadoVehiculo.setText("Disponible");
            estadoVehiculo.setVisibility(View.INVISIBLE);
        }

        vehiculoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbVehiculos db = new dbVehiculos(verVehiculo.this);

                if(id != ""){
                    db.updateVehiculo(id,placaVehiculo.getText().toString(), tipoVehiculo.getSelectedItem().toString(), estadoVehiculo.getText().toString(), nombreVehiculo.getText().toString());
                }else {
                    db.createVehiculo(placaVehiculo.getText().toString(), tipoVehiculo.getSelectedItem().toString(), estadoVehiculo.getText().toString(), nombreVehiculo.getText().toString());
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
                db.deleteVehiculo(id);
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