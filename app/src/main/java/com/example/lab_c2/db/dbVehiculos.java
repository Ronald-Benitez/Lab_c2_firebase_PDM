package com.example.lab_c2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.lab_c2.entidades.vehiculo;

import java.util.ArrayList;

public class dbVehiculos extends dbHelper{
    Context context;
    public dbVehiculos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long createVehiculo(String placa, String tipo, String estado,String nombre){
        long id=0;

        try{
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getReadableDatabase();

            ContentValues values= new ContentValues();
            values.put("placa",placa);
            values.put("tipo",tipo);
            values.put("estado",estado);
            values.put("nombre",nombre);

            id = db.insert(TABLA_VEHICULOS,null,values);
            db.close();
        }catch (Exception e){
            e.toString();
        }

        return id;
    }

    public ArrayList<vehiculo> readVehiculos (){
        ArrayList<vehiculo> lista = new ArrayList<>();
        vehiculo ve = null;
        Cursor cursorVehiculos = null;

        try{

            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            cursorVehiculos = db.rawQuery("SELECT * FROM "+TABLA_VEHICULOS,null);

            if(cursorVehiculos.moveToFirst()){
                do{
                    ve = new vehiculo();
                    ve.setId(cursorVehiculos.getInt(0));
                    ve.setPlaca(cursorVehiculos.getString(1));
                    ve.setTipo(cursorVehiculos.getString(2));
                    ve.setEstado(cursorVehiculos.getString(3));
                    ve.setNombre(cursorVehiculos.getString(4));

                    lista.add(ve);
                }while(cursorVehiculos.moveToNext());
            }
            db.close();
        }catch (Exception e){
            e.toString();
        }
        cursorVehiculos.close();
        return  lista;
    }

    public vehiculo findVehiculo(String clave, String valor){
        vehiculo ve = null;
        Cursor cursorVehiculo = null;

        try {
            dbHelper DBHelper = new dbHelper(context);
            SQLiteDatabase db = DBHelper.getReadableDatabase();
            cursorVehiculo = db.rawQuery("SELECT * FROM "+TABLA_VEHICULOS+" WHERE "+clave+" = \""+valor+"\" LIMIT 1",null);
            Toast.makeText(context, "SELECT * FROM "+TABLA_VEHICULOS+" WHERE "+clave+" = \""+valor+"\" LIMIT 1", Toast.LENGTH_SHORT).show();
            if(cursorVehiculo.moveToFirst()){
                ve = new vehiculo();
                ve.setId(cursorVehiculo.getInt(0));
                ve.setPlaca(cursorVehiculo.getString(1));
                ve.setTipo(cursorVehiculo.getString(2));
                ve.setEstado(cursorVehiculo.getString(3));
                ve.setNombre(cursorVehiculo.getString(4));
            }

        }catch (Exception e){
            e.toString();
        }

        cursorVehiculo.close();
        return  ve;
    }

    public boolean updateVehiculo(int id,String placa, String tipo, String estado,String nombre){
        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();

        boolean updated = false;

        try {
            ContentValues values= new ContentValues();
            values.put("placa",placa);
            values.put("tipo",tipo);
            values.put("estado",estado);
            values.put("nombre",nombre);

            db.update(TABLA_VEHICULOS,values,"idV=?",new String[]{String.valueOf(id)});
            updated = true;
        }catch (Exception e){
            updated = false;
            e.toString();
        }finally {
            db.close();
        }

        return updated;
    }

    public boolean deleteVehiculo(int id){
        dbHelper DBHelper = new dbHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();

        boolean removed = false;

        try{
            db.delete(TABLA_VEHICULOS,"idV=?",new String[]{String.valueOf(id)});
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
