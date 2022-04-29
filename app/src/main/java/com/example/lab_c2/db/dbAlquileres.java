package com.example.lab_c2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.lab_c2.entidades.Alquiler;
import com.example.lab_c2.entidades.alquilerLista;
import com.example.lab_c2.entidades.vehiculo;

import java.util.ArrayList;

public class dbAlquileres extends dbHelper{
    Context context;
    public dbAlquileres(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public long createAlquiler(String fechaInicio, String fechaFin,String tiempoAlquiler,String precioAlquiler,String idV,String idC){
        long id=0;

        try{
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getReadableDatabase();

            ContentValues values= new ContentValues();
            values.put("fechaInicio",fechaInicio);
            values.put("fechaFin",fechaFin);
            values.put("tiempoAlquiler",tiempoAlquiler);
            values.put("precioAlquiler",precioAlquiler);
            values.put("idV",idV);
            values.put("idC",idC);

            id = db.insert(TABLA_ALQUILERES,null,values);
            db.close();
        }catch (Exception e){
            e.toString();
        }

        return id;
    }

    public ArrayList<alquilerLista> readListAlquiler(){
        ArrayList<alquilerLista> lista = new ArrayList<>();
        alquilerLista al = null;
        Cursor cursorVehiculos = null;

        try{

            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            cursorVehiculos = db.rawQuery("SELECT alquileres.idA,vehiculos.nombre as \"nombreV\",clientes.nombre as \"nombreC\" FROM alquileres INNER JOIN vehiculos  ON alquileres.idV = vehiculos.idV INNER JOIN clientes ON alquileres.idC = clientes.idC",null);

            if(cursorVehiculos.moveToFirst()){
                do{
                    al = new alquilerLista();
                    al.setIdAlquiler(cursorVehiculos.getInt(0));
                    al.setNombreV(cursorVehiculos.getString(1));
                    al.setNombreC(cursorVehiculos.getString(2));

                    lista.add(al);
                }while(cursorVehiculos.moveToNext());
            }
            db.close();
        }catch (Exception e){
            e.toString();
        }
        cursorVehiculos.close();
        return  lista;
    }

    public Alquiler findAlquiler(String clave, String valor){
        Alquiler al = null;
        Cursor cursorAlquiler = null;

        try {
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            cursorAlquiler = db.rawQuery("SELECT * FROM "+TABLA_ALQUILERES+" WHERE "+clave+" = \""+valor+"\" LIMIT 1",null);
            //Toast.makeText(context, "SELECT * FROM "+TABLA_VEHICULOS+" WHERE "+clave+" = \""+valor+"\" LIMIT 1", Toast.LENGTH_SHORT).show();
            if(cursorAlquiler.moveToFirst()){
                al = new Alquiler();
                al.setIdA(cursorAlquiler.getInt(0));
                al.setFecchaInicio(cursorAlquiler.getString(1));
                al.setFecchaFin(cursorAlquiler.getString(2));
                al.setTiempoAlquiler(cursorAlquiler.getString(3));
                al.setPrecioAlquiler(cursorAlquiler.getString(4));
                al.setIdV(cursorAlquiler.getInt(5));
                al.setIdC(cursorAlquiler.getInt(6));
            }

        }catch (Exception e){
            e.toString();
        }

        cursorAlquiler.close();
        return  al;
    }

    public boolean updateAlquiler(int id,String fechaInicio, String fechaFin,String tiempoAlquiler,String precioAlquiler,String idV,String idC){
        boolean updated = false;

        try{
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getReadableDatabase();

            ContentValues values= new ContentValues();
            values.put("fechaInicio",fechaInicio);
            values.put("fechaFin",fechaFin);
            values.put("tiempoAlquiler",tiempoAlquiler);
            values.put("precioAlquiler",precioAlquiler);
            values.put("idV",idV);
            values.put("idC",idC);

            db.update(TABLA_ALQUILERES,values,"idA = "+id,null);
            updated=true;
            db.close();
        }catch (Exception e){
            updated = false;
            e.toString();
        }

        return updated;
    }

    public boolean deleteAlquiler(int id){
        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();

        boolean removed = false;

        try{
            db.delete(TABLA_ALQUILERES,"idA=?",new String[]{String.valueOf(id)});
            removed = true;
        }catch (Exception e){
            removed = false;
            e.toString();
        }finally {
            db.close();
        }

        return removed;
    }
}
