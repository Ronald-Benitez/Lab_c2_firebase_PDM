package com.example.lab_c2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_c2.adapters.lista_VehiculosCliente_adapter;
import com.example.lab_c2.adapters.lista_alquileres_adapter;
import com.example.lab_c2.db.dbClientes;
import com.example.lab_c2.entidades.Clientes;
import com.example.lab_c2.entidades.alquilerLista;
import com.example.lab_c2.entidades.vehiculosCliente;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class verCliente extends AppCompatActivity {
    private TextView idtext,txtAA; //textViewID
    private EditText nombretxt;
    private Button editarBtn,eliminarBtn;
    Clientes cliente;

    RecyclerView listaVehiculosCliente;
    ArrayList<vehiculosCliente> listaCCvehiculos;

    boolean modoEdicion = false;
    String id="";
    String nombreCliente = "";

    FirebaseFirestore dbF = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_ver_cliente);

        idtext = findViewById(R.id.textViewID);
        txtAA = findViewById(R.id.txtautosAlquyilados);
        nombretxt = findViewById(R.id.NombreClientetxt2);
        editarBtn = findViewById(R.id.botonActulizarCliente);
        eliminarBtn = findViewById(R.id.botonEliminarCliente);
        listaVehiculosCliente =findViewById(R.id.vehiculosDeUsuarios);
        listaVehiculosCliente.setLayoutManager( new LinearLayoutManager(this));



        if(extras!=null) {
            id = extras.getString("id");
            nombreCliente = extras.getString("nombreCC");
            modoEdicion = extras.getBoolean("editar");

           DocumentReference docRef = dbF.collection("clientes").document(id);

            docRef.get().addOnSuccessListener(documentSnapshot -> {
                cliente = documentSnapshot.toObject(Clientes.class);
                idtext.setText(cliente.getId());
                nombretxt.setText(cliente.getNombre());

            });
        }


         if (cliente !=null){
             nombretxt.setText(cliente.getNombre());
             idtext.setText(String.valueOf(cliente.getId()));
         }
         if (!modoEdicion){
             getData();

             ((ViewManager)editarBtn.getParent()).removeView(editarBtn);
             ((ViewManager)eliminarBtn.getParent()).removeView(eliminarBtn);
             nombretxt.setInputType(InputType.TYPE_NULL);


         }else {
             txtAA.setVisibility(View.INVISIBLE);
         }


    }

    //EVENTOS DE BOTONES

    public void btonEditarCliente(View view){

        if (!nombretxt.getText().toString().equals("")){
            dbClientes dbclientes = new dbClientes(verCliente.this);
            dbclientes.editarCliente(id,nombretxt.getText().toString());

        }else{
            Toast.makeText(this, "Parametros vacios!", Toast.LENGTH_SHORT).show();
        }

    }

    public void btonEliminarCliente(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(verCliente.this);
        builder.setMessage("Eliminar el contacto?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbClientes dbclientes = new dbClientes(verCliente.this);
                        dbclientes.eliminarClinete(id);

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }

    public void getData(){
        listaCCvehiculos = new ArrayList<>();
        dbF.collection("alquileres").whereEqualTo("nombreC",nombreCliente).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            vehiculosCliente v = document.toObject(vehiculosCliente.class);
                            listaCCvehiculos.add(v);
                        }
                        lista_VehiculosCliente_adapter adapter = new lista_VehiculosCliente_adapter(listaCCvehiculos);
                        listaVehiculosCliente.setAdapter(adapter);
                    }
                }
            }
        });
    }

}