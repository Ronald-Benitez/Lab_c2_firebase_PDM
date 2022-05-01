package com.example.lab_c2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_c2.db.dbClientes;
import com.example.lab_c2.entidades.Clientes;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class verCliente extends AppCompatActivity {
    private TextView idtext; //textViewID
    private EditText nombretxt;
    private Button editarBtn,eliminarBtn;
    Clientes cliente;

    boolean modoEdicion = false;
    String id="";

    FirebaseFirestore dbF = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_ver_cliente);

        idtext = findViewById(R.id.textViewID);
        nombretxt = findViewById(R.id.NombreClientetxt2);
        editarBtn = findViewById(R.id.botonActulizarCliente);
        eliminarBtn = findViewById(R.id.botonEliminarCliente);

        if(extras!=null) {
            id = extras.getString("id");
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
             editarBtn.setVisibility(View.INVISIBLE);
             eliminarBtn.setVisibility(View.INVISIBLE);
             nombretxt.setInputType(InputType.TYPE_NULL);
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

}