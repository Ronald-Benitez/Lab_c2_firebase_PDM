package com.example.lab_c2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_c2.adapters.lista_vehiculos_adapter;
import com.example.lab_c2.db.dbAlquileres;
import com.example.lab_c2.db.dbClientes;
import com.example.lab_c2.db.dbVehiculos;
import com.example.lab_c2.entidades.Alquiler;
import com.example.lab_c2.entidades.Clientes;
import com.example.lab_c2.entidades.vehiculo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistroAlquiler extends AppCompatActivity {
    Button registro,delete;
    TextView precioAlquiler,fechaInicio, fechaFin,tiempoAlquiler;
    Spinner idVehiculo,idCliente;
    String id="";
    String nVehiculo="";

    ArrayList<String> listVehiculos,listClientes;
    dbVehiculos vehiculosDB = new dbVehiculos(this);
    dbAlquileres alquileresDB = new dbAlquileres(this);
    dbAlquileres alquilerDB = new dbAlquileres(this);

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    DatePickerDialog.OnDateSetListener setDateListener,setDateListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_registro_alquiler);

        registro = findViewById(R.id.registroAl);
        fechaInicio = findViewById(R.id.fechaInicio);
        fechaFin = findViewById(R.id.fechaFin);
        tiempoAlquiler = findViewById(R.id.tiempoAlquiler);
        precioAlquiler = findViewById(R.id.precioAlquiler);
        idVehiculo = findViewById(R.id.idVehiculo);
        idCliente = findViewById(R.id.idCliente);
        delete = findViewById(R.id.deleteAlquiler);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        getVehiculos();
        getClientes();

        if(extras!=null){
            id = extras.getString("ID");
            registro.setText("Actualizar");
            delete.setVisibility(View.VISIBLE);
            DocumentReference docRef = db.collection("alquileres").document(id);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Alquiler alquiler = documentSnapshot.toObject(Alquiler.class);
                    fechaInicio.setText(alquiler.getFechaInicio());
                    fechaFin.setText(alquiler.getFechaFin());
                    tiempoAlquiler.setText(alquiler.getTiempoAlquiler());
                    precioAlquiler.setText(alquiler.getPrecioAlquiler());
                    idVehiculo.setSelection(listVehiculos.indexOf(alquiler.getNombreV()));
                    idCliente.setSelection(listClientes.indexOf(alquiler.getNombreC()));

                    nVehiculo = alquiler.getNombreV();
                }
            });


        }



        if(extras!=null){
            idVehiculo.setSelection(listVehiculos.size()-1);
            idCliente.setSelection(listClientes.size()-1);
        }

        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroAlquiler.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setDateListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

                ;
            }
        });

        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistroAlquiler.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setDateListener2, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        idVehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    getPrecioAlquiler();
                }catch (Exception e){
                    e.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        setDateListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = "";

                if(i2<10){
                    date+="0"+i2;
                }else{
                    date+=i2;
                }

                if(i1<10){
                    date+="/0"+i1;
                }else{
                    date+="/"+i1;
                }

                date += "/"+i;
                fechaInicio.setText(date);

                try {
                    setTiempoAlquiler();
                    getPrecioAlquiler();
                }catch (Exception e){
                    e.toString();
                }
            }
        };

        setDateListener2 = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = "";

                if(i2<10){
                    date+="0"+i2;
                }else{
                    date+=i2;
                }

                if(i1<10){
                    date+="/0"+i1;
                }else{
                    date+="/"+i1;
                }

                date += "/"+i;
                fechaFin.setText(date);

                try {
                    setTiempoAlquiler();
                    getPrecioAlquiler();
                }catch (Exception e){
                    e.toString();
                }
            }
        };

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idV = "";
                String idC = "";
                String fechaI = "";
                String fechaF = "";
                String tiempoAl = "";
                String precioAl = "";

                try {
                    idV = idVehiculo.getSelectedItem().toString().split("-")[0];
                    idC = idCliente.getSelectedItem().toString().split("-")[0];
                    fechaI = fechaInicio.getText().toString();
                    fechaF = fechaFin.getText().toString();
                    tiempoAl = tiempoAlquiler.getText().toString();
                    precioAl = precioAlquiler.getText().toString();
                }catch (Exception e){
                    e.toString();
                }

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

                if(id != "") {
                    if (!campoVacio) {
                        alquilerDB.updateAlquiler(id, fechaI, fechaF, tiempoAl, precioAl,idC,idV);

                        if(nVehiculo!=idV){

                            db.collection("vehiculos").whereEqualTo("nombre",idVehiculo.getSelectedItem().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                    vehiculo ve= documentSnapshot.toObject(vehiculo.class);
                                    ve.setEstado("Alquilado");
                                    vehiculosDB.updateVehiculo(ve);
                                }

                            });

                            db.collection("vehiculos").whereEqualTo("nombre",nVehiculo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                    vehiculo ve= documentSnapshot.toObject(vehiculo.class);
                                    ve.setEstado("Disponible");
                                    vehiculosDB.updateVehiculo(ve);
                                }

                            });

                        }

                    } else {
                        Toast.makeText(RegistroAlquiler.this, "No debe dejar campos vacios", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    if (!campoVacio) {
                        alquilerDB.createAlquiler(fechaI, fechaF, tiempoAl, precioAl, idV, idC);
                        db.collection("vehiculos").whereEqualTo("nombre",idVehiculo.getSelectedItem().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                vehiculo ve= documentSnapshot.toObject(vehiculo.class);
                                ve.setEstado("Alquilado");
                                vehiculosDB.updateVehiculo(ve);
                            }

                        });
                    } else {
                        Toast.makeText(RegistroAlquiler.this, "No debe dejar campos vacios", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alquilerDB.deleteAlquiler(id,idVehiculo.getSelectedItem().toString());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setTiempoAlquiler(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inicioD = LocalDate.parse(fechaInicio.getText().toString(),formatter);
        LocalDate finalD = LocalDate.parse(fechaFin.getText().toString(),formatter);

        Period periodo = null;
        String tiempo = "";

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                periodo = Period.between(inicioD, finalD);
                tiempo=String.valueOf(periodo.getYears()*365+(finalD.getDayOfYear()-inicioD.getDayOfYear()));
            }
        } catch (Exception e) {
            e.toString();
        }

        tiempoAlquiler.setText(tiempo);
    }

    public void getPrecioAlquiler(){
        int tiempo = Integer.parseInt(tiempoAlquiler.getText().toString());

        if (tiempo != 0) {

            db.collection("vehiculos").whereEqualTo("nombre",idVehiculo.getSelectedItem().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                    String tipo = documentSnapshot.getString("tipo");
                    int precio = 0;
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
                    precioAlquiler.setText(String.valueOf(precio));
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegistroAlquiler.this, "Error al calcular el precio", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void getVehiculos(){
        listVehiculos = new ArrayList<>();

        db.collection("vehiculos")
                .whereEqualTo("estado","Disponible")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                listVehiculos.add(document.getString("nombre"));
                            }
                            ArrayAdapter<String> adapterV = new ArrayAdapter<String>(RegistroAlquiler.this, android.R.layout.simple_spinner_item, listVehiculos);
                            idVehiculo.setAdapter(adapterV);


                        }
                    }
                });
    }


    public void getClientes(){
        listClientes = new ArrayList<>();

        db.collection("clientes")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                listClientes.add(document.getString("nombre"));
                            }
                            ArrayAdapter<String> adapterC = new ArrayAdapter<String>(RegistroAlquiler.this, android.R.layout.simple_spinner_item, listClientes);

                            idCliente.setAdapter(adapterC);
                        }
                    }
                });
    }


}