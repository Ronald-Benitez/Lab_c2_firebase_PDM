package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab_c2.db.dbClientes;
import com.example.lab_c2.entidades.Clientes;

public class verCliente extends AppCompatActivity {
    private TextView idtext; //textViewID
    private EditText nombretxt;
    private Button editarBtn,eliminarBtn;
    Clientes cliente;
    boolean pasar =false;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cliente);

        idtext = findViewById(R.id.textViewID);
        nombretxt = findViewById(R.id.NombreClientetxt2);
        editarBtn = findViewById(R.id.botonActulizarCliente);
         if(savedInstanceState ==null){
             Bundle extras = getIntent().getExtras();
             if (extras==null){
                 id = Integer.parseInt(null);
             }else{
                 id = extras.getInt("id");
             }
         }else{
             id = (int) savedInstanceState.getSerializable("id");
         }

        dbClientes dbclientes = new dbClientes(verCliente.this);
         cliente = dbclientes.mostrarCliente(id);

         if (cliente !=null){
             nombretxt.setText(cliente.getNombre());
             idtext.setText(String.valueOf(cliente.getId()));
         }


    }

    //EVENTOS DE BOTONES

    public void btonEditarCliente(View view){
        if (!nombretxt.getText().toString().equals("")){
            dbClientes dbclientes = new dbClientes(verCliente.this);
            pasar = dbclientes.editarCliente(id,nombretxt.getText().toString());

            if (pasar){
                Toast.makeText(this, "REGISTRO EDITADO CON EXITO", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "ERROR EN LA MODIFICAION DEL CLIENTE", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Parametros vacios!", Toast.LENGTH_SHORT).show();
        }

    }

    public void btonEliminarCliente(View view){

    }
}