package com.example.lab_c2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab_c2.adapters.lista_clientes_adapter;
import com.example.lab_c2.adapters.lista_vehiculos_adapter;
import com.example.lab_c2.db.dbClientes;
import com.example.lab_c2.entidades.Clientes;
import com.example.lab_c2.entidades.vehiculo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Clientes_Activity extends AppCompatActivity {
    private EditText txtNombre;
    private RecyclerView listaClientes;
    ArrayList<Clientes> listaArrayClientes;
    FirebaseFirestore dbF = FirebaseFirestore.getInstance();
    dbClientes db = new dbClientes(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        txtNombre = findViewById(R.id.nombreEditText);
        listaClientes = findViewById(R.id.listaClientes);
        listaClientes.setLayoutManager( new LinearLayoutManager(this));

        getData();



        //lista_clientes_adapter adapter = new lista_clientes_adapter(dbCliente.mostrarClientes());
        //listaClientes.setAdapter(adapter);
    }

    public void agregarCliente(View view){
        dbClientes dbclientes = new dbClientes(Clientes_Activity.this);
        dbclientes.insertarCliente(txtNombre.getText().toString());
        limpiarFormulario();
        getData();
    }

    private void limpiarFormulario(){
        txtNombre.setText("");
    }

    public void getData(){
        listaArrayClientes = new ArrayList<>();

        dbF.collection("clientes")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Clientes c = document.toObject(Clientes.class);
                                listaArrayClientes.add(c);
                            }
                            lista_clientes_adapter adapter = new lista_clientes_adapter(listaArrayClientes);
                            listaClientes.setAdapter(adapter);
                        }
                    }
                });
    }
}