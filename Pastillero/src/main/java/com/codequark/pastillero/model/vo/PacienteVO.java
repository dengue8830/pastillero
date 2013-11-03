package com.codequark.pastillero.model.vo;

/**
 * Created by dengue8830 on 10/6/13.
 */
public class PacienteVO {
    public static final String COLUMN_ID_PACIENTE = "idPaciente";
    public static final String COLUMN_ID_IMAGEN = "idImagen";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APELLIDO = "apellido";
    public static final String COLUMN_DNI = "dni";
    public static final String COLUMN_EDAD = "edad";

    private int  idImagen;
    private long idPaciente;
    private String nombre;
    private String apellido;
    private String dni;
    private short edad;

    public PacienteVO(int idImagen, long idPaciente, String nombre, String apellido,short edad, String dni){
        this.idImagen = idImagen;
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
    }

    public PacienteVO() {}

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public short getEdad() {
        return edad;
    }

    public void setEdad(short edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidoComaEspacioNombre(){
        return this.apellido+", "+this.nombre;
    }

    public static String[] getAllColumns(){
        String[] columnas = {COLUMN_ID_PACIENTE, COLUMN_ID_IMAGEN, COLUMN_APELLIDO, COLUMN_NOMBRE, COLUMN_EDAD, COLUMN_DNI};
        return columnas;
    }
}
