package com.example.lab_c2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.lab_c2.Clientes_Activity;
import com.example.lab_c2.entidades.Clientes;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class dbClientes extends dbHelper{

    Context context;

    public dbClientes(@Nullable Context context) {
        super(context);
        this.context = context;

    }

    public long insertarCliente(String nombre){
        long id=0;
        try{


        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre",nombre);

        id = db.insert(TABLA_CLIENTES,null,values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }

    public ArrayList<Clientes> mostrarClientes(){
        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        ArrayList<Clientes> listaClientes = new ArrayList<>();
        Clientes cliente = null;
        Cursor cursorClientes = null;

        cursorClientes = db.rawQuery("SELECT * FROM "+TABLA_CLIENTES,null);
        if (cursorClientes.moveToFirst()){
            do {
                cliente = new Clientes();
                cliente.setId(cursorClientes.getInt(0));
                cliente.setNombre(cursorClientes.getString(1));
                listaClientes.add(cliente);
            }while (cursorClientes.moveToNext());

        }
        cursorClientes.close();
        return listaClientes;
    }

    public ArrayList<String> spinnerClientes(){
        ArrayList<String> lista = new ArrayList<>();
        String ve = null;
        Cursor cursorClientes = null;

        try{

            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            cursorClientes = db.rawQuery("SELECT idC,nombre FROM "+TABLA_CLIENTES,null);

            if(cursorClientes.moveToFirst()){
                do{
                    ve = String.valueOf(cursorClientes.getInt(0));
                    ve += "-";
                    ve += cursorClientes.getString(1);
                    lista.add(ve);
                }while(cursorClientes.moveToNext());
            }
            db.close();
        }catch (Exception e){
            e.toString();
        }
        cursorClientes.close();
        return  lista;
    }

    public Clientes mostrarCliente(int idc){
        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();


        Clientes cliente =null;
        Cursor cursorClientes;

        cursorClientes = db.rawQuery("SELECT * FROM "+TABLA_CLIENTES + " WHERE idc = "+idc + " LIMIT 1",null);
        if (cursorClientes.moveToFirst()){

                cliente = new Clientes();
                cliente.setId(cursorClientes.getInt(0));
                cliente.setNombre(cursorClientes.getString(1));


        }
        cursorClientes.close();
        return cliente;
    }

    public boolean editarCliente(int id,String nombre){
        boolean encontrado =false;
        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE " + TABLA_CLIENTES + " SET nombre='" +nombre+ "' WHERE idc='"+id+"' ");
            encontrado = true;
        }catch (Exception ex){
            ex.toString();
        }finally {
             db.close();
        }


        return encontrado;
    }

    public boolean eliminarClinete(int id){
        boolean encontrado = false;
        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        try{
            db.execSQL("DELETE FROM "+TABLA_CLIENTES+" WHERE idc = '"+id+"'");
            encontrado = true;
        }catch (Exception ex){
            ex.toString();
        }finally {
            db.close();
        }


        return encontrado;

    }
}
