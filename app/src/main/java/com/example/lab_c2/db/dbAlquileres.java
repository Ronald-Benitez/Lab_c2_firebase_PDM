package com.example.lab_c2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

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
}
