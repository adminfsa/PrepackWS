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
public class Message {
    protected int codigoError;
    protected String mensajeError;

    public Message() {
    }
    
    public Message(int codigoError, String mensajeError) {
        this.codigoError = codigoError;
        this.mensajeError = mensajeError;
    }

    public int getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(int codigoError) {
        this.codigoError = codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
