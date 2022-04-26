package com.example.lab_c2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alquiler.db";
    public static final String TABLA_VEHICULOS = "vehiculos";
    public static final String TABLA_CLIENTES = "clientes";
    public static final String TABLA_ALQUILERES = "alquileres";

    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Tabla vehiculos
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLA_VEHICULOS+"("+
                "idV INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "placa TEXT,"+
                "tipo TEXT,"+
                "estado TEXT,"+
                "nombre TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLA_CLIENTES+"("+
                "idC INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLA_ALQUILERES+"("+
                "idA INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "fechaInicio TEXT,"+
                "fechaFin TEXT,"+
                "tiempoAlquiler INTEGER,"+
                "precioAlquiler REAL,"+
                "idV INTEGER,"+
                "idC INTEGER,"+
                "FOREIGN KEY(idV) REFERENCES vehiculos(idV),"+
                "FOREIGN KEY(idC) REFERENCES clientes(idC))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLA_VEHICULOS);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLA_CLIENTES);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLA_ALQUILERES);
        onCreate(sqLiteDatabase);
    }
}
