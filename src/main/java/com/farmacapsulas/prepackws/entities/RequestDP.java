/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farmacapsulas.prepackws.entities;

/**
 *
 * @author wsalgado
 */
public class RequestDP {
    private int numeroOrden;
    private String usuario;

    public RequestDP() {
    }
    
    public RequestDP(int numeroOrden, String usuario) {
        this.numeroOrden = numeroOrden;
        this.usuario = usuario;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
