/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farmacapsulas.prepackws.util;

/**
 *
 * @author wsalgado
 */
public class PrepackException  extends Exception{
    private int codigoError;
    private String mensajeError;

    public PrepackException() {
    }
    
    public PrepackException(int codigoError, String mensajeError) {
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
