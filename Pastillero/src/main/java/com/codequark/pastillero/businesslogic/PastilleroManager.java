package com.codequark.pastillero.businesslogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.codequark.pastillero.MainActivity;
import com.codequark.pastillero.R;
import com.codequark.pastillero.dataBase.MyDBHelper;
import com.codequark.pastillero.lista.ListaEntrada;
import com.codequark.pastillero.model.vo.PacienteVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengue8830 on 10/22/13.
 */
/*
    No usamos el patron DAO porque al no tener un inyector de dependencias ni algo que las
    gestione, se producira un gran gasto de recursos.
*/

public class PastilleroManager {
    private SQLiteDatabase dataBase;
    private MyDBHelper dbHelper;

    public PastilleroManager(Context context) {
        dbHelper = new MyDBHelper(context);
    }

    public void openDB() throws SQLException {
        dataBase = dbHelper.getWritableDatabase();
    }

    public void closeDB() {
        dbHelper.close();
    }

    public long insertPaciente(PacienteVO pacienteVO){
        ContentValues values = new ContentValues();

        values.put(PacienteVO.COLUMN_ID_IMAGEN, pacienteVO.getIdImagen());
        values.put(PacienteVO.COLUMN_APELLIDO, pacienteVO.getApellido());
        values.put(PacienteVO.COLUMN_NOMBRE, pacienteVO.getNombre());
        values.put(PacienteVO.COLUMN_EDAD, pacienteVO.getEdad());
        values.put(PacienteVO.COLUMN_DNI, pacienteVO.getDni());

        return dataBase.insert(MyDBHelper.TABLE_PACIENTES, null, values);
    }

    public int updatePaciente(PacienteVO pacienteVO){
        ContentValues values = new ContentValues();

        values.put(PacienteVO.COLUMN_ID_PACIENTE, pacienteVO.getIdPaciente());
        values.put(PacienteVO.COLUMN_ID_IMAGEN, pacienteVO.getIdImagen());
        values.put(PacienteVO.COLUMN_APELLIDO, pacienteVO.getApellido());
        values.put(PacienteVO.COLUMN_NOMBRE, pacienteVO.getNombre());
        values.put(PacienteVO.COLUMN_EDAD, pacienteVO.getEdad());
        values.put(PacienteVO.COLUMN_DNI, pacienteVO.getDni());

        return dataBase.update(MyDBHelper.TABLE_PACIENTES, values, PacienteVO.COLUMN_ID_PACIENTE + " = ? ", new String[]{String.valueOf(pacienteVO.getIdPaciente())});
    }


    //Devuelve la cantidad de filas afectadas
    public int borrarPaciente(long id) throws Exception{
        try {
            return dataBase.delete(MyDBHelper.TABLE_PACIENTES, PacienteVO.COLUMN_ID_PACIENTE + " = ? ", new String[]{String.valueOf(id)});
        }catch (Exception e){
            throw e;
        }

    }

    public ArrayList<PacienteVO> getAllpacientes(){
        ArrayList<PacienteVO> listaPacientes = new ArrayList<PacienteVO>();
        Cursor cursor = dataBase.query(MyDBHelper.TABLE_PACIENTES, PacienteVO.getAllColumns(), null, null, null, null, null);

        if(cursor == null){
            return listaPacientes;
        }

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            PacienteVO pacienteVO = new PacienteVO();
            pacienteVO.setIdPaciente(cursor.getLong(0));
            pacienteVO.setIdImagen(cursor.getInt(1));
            pacienteVO.setApellido(cursor.getString(2));
            pacienteVO.setNombre(cursor.getString(3));
            pacienteVO.setEdad(cursor.getShort(4));
            pacienteVO.setDni(cursor.getString(5));
            listaPacientes.add(pacienteVO);

            cursor.moveToNext();
        }

        cursor.close();

        return listaPacientes;
    }

    public PacienteVO getPacienteById(long idPaciente){
        Cursor cursor = dataBase.query(MyDBHelper.TABLE_PACIENTES, PacienteVO.getAllColumns(), PacienteVO.COLUMN_ID_PACIENTE + " = ? ", new String[]{String.valueOf(idPaciente)}, null, null, null);
        PacienteVO pacienteVO = new PacienteVO();

        if(cursor == null){
            return pacienteVO;
        }

        cursor.moveToFirst();

        pacienteVO.setIdPaciente(cursor.getLong(0));
        pacienteVO.setIdImagen(cursor.getInt(1));
        pacienteVO.setApellido(cursor.getString(2));
        pacienteVO.setNombre(cursor.getString(3));
        pacienteVO.setEdad(cursor.getShort(4));
        pacienteVO.setDni(cursor.getString(5));

        cursor.close();

        return pacienteVO;
    }

    public ArrayList<ListaEntrada> cargarListaEntradas(ArrayList<PacienteVO> pacientesVO, String opcion){
        ArrayList<ListaEntrada> listaEntradas = new ArrayList<ListaEntrada>();

        ListaEntrada entrada = null;
        
        for (PacienteVO unPacienteVO : pacientesVO){
            entrada = new ListaEntrada(unPacienteVO.getIdImagen(), unPacienteVO.getApellidoComaEspacioNombre(), "", unPacienteVO, MainActivity.NORMAL);
            listaEntradas.add(entrada);
        }

        entrada = new ListaEntrada(R.drawable.icono_agregar_persona, opcion, "", null, MainActivity.AGREGAR);
        listaEntradas.add(entrada);

        return listaEntradas;
    }
}
