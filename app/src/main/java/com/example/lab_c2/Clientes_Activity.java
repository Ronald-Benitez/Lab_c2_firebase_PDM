package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab_c2.adapters.lista_clientes_adapter;
import com.example.lab_c2.db.dbClientes;
import com.example.lab_c2.entidades.Clientes;

import java.lang.reflect.Array;
import java.security.PrivilegedAction;
import java.util.ArrayList;

public class Clientes_Activity extends AppCompatActivity {
    private EditText txtNombre;
    private RecyclerView listaClientes;
    ArrayList<Clientes> listaArrayClientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        txtNombre = findViewById(R.id.nombreEditText);
        listaClientes = findViewById(R.id.listaClientes);
        listaClientes.setLayoutManager( new LinearLayoutManager(this));

        dbClientes dbCliente = new dbClientes(Clientes_Activity.this);
        listaArrayClientes = new ArrayList<>();
        lista_clientes_adapter adapter = new lista_clientes_adapter(dbCliente.mostrarContactos());
        listaClientes.setAdapter(adapter);
    }

    public void agregarCliente(View view){
        dbClientes dbclientes = new dbClientes(Clientes_Activity.this);
        long id= dbclientes.insertarCliente(txtNombre.getText().toString());
        if (id >0){
            Toast.makeText(this, "CLIENTE REGISTRADO", Toast.LENGTH_SHORT).show();
            limpiarFormulario();
        }else{
            Toast.makeText(this, "ERROR AL REGISTRAR AL CLIENTE", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarFormulario(){
        txtNombre.setText("");
    }
}