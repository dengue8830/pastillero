package com.codequark.pastillero.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codequark.pastillero.model.vo.PacienteVO;

/**
 * Created by dengue8830 on 10/21/13.
 */
public class MyDBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "pastillero";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_PACIENTES = "pacientes";
//    public static final String TABLE_REMEDIO = "pacientes";
//    public static final String TABLE_ALARMA = "pacientes";
    public static final String SQL_TABLE_CREATE = "create table "
            + TABLE_PACIENTES +" ("
            + PacienteVO.COLUMN_ID_PACIENTE +" integer primary key autoincrement, "
            + PacienteVO.COLUMN_ID_IMAGEN +" integer,"
            + PacienteVO.COLUMN_NOMBRE +" text not null,"
            + PacienteVO.COLUMN_APELLIDO +" text not null,"
            + PacienteVO.COLUMN_EDAD +" integer,"
            + PacienteVO.COLUMN_DNI +" text);";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        dataBase.execSQL(SQL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
