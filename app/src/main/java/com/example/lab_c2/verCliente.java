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

public class verCliente extends AppCompatActivity {
    private TextView idtext; //textViewID
    private EditText nombretxt;
    private Button editarBtn,eliminarBtn;
    Clientes cliente;

    boolean pasar =false;
    boolean modoEdicion = false;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cliente);

        idtext = findViewById(R.id.textViewID);
        nombretxt = findViewById(R.id.NombreClientetxt2);
        editarBtn = findViewById(R.id.botonActulizarCliente);
        eliminarBtn = findViewById(R.id.botonEliminarCliente);


         if(savedInstanceState ==null){
             Bundle extras = getIntent().getExtras();
             if (extras==null){
                 id = Integer.parseInt(null);
             }else{
                 modoEdicion = extras.getBoolean("editar");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(verCliente.this);
        builder.setMessage("Eliminar el contacto?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbClientes dbclientes = new dbClientes(verCliente.this);
                        if (dbclientes.eliminarClinete(id)){
                            //regresar al la vista
                            Toast.makeText(verCliente.this, "CLIENTE ELIMINADO CONE EXITO!", Toast.LENGTH_SHORT).show();
                            principal();
                            
                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();


    }
    private void principal(){
        Intent intent = new Intent(this, Clientes_Activity.class);
        startActivity(intent);
    }
}