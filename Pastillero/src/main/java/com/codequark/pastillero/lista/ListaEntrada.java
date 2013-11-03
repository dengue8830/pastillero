package com.codequark.pastillero.lista;

import com.codequark.pastillero.model.vo.PacienteVO;

/**
 * Created by dengue8830 on 10/16/13.
 */
public class ListaEntrada {
    private int idImagen;
    private String textoEncima;
    private String textoDebajo;
    private long tipo;
    private PacienteVO pacienteVo;

    public ListaEntrada(int idImagen, String textoEncima, String textoDebajo, PacienteVO pacienteVo, long tipo) {
        this.setIdImagen(idImagen);
        this.textoEncima = textoEncima;
        this.textoDebajo = textoDebajo;
        this.pacienteVo = pacienteVo;
        this.tipo = tipo;
    }

    public String getTextoEncima() {
        return textoEncima;
    }

    public String getTextoDebajo() {
        return textoDebajo;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public PacienteVO getPacienteVo() {
        return pacienteVo;
    }

    public void setPacienteVo(PacienteVO pacienteVo) {
        this.pacienteVo = pacienteVo;
    }

    public long getTipo() {
        return tipo;
    }

    public void setTipo(long tipo) {
        this.tipo = tipo;
    }
}
