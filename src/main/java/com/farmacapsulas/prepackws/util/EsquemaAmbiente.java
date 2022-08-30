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
public enum EsquemaAmbiente {
    PRUEBAS("FACLX833F"), PRODUCCION("ERPLX833F");
    
    private String esquema;

    private EsquemaAmbiente(String esquema) {
        this.esquema = esquema;
    }

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }
}
