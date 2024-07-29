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
    PRUEBAS("FACLX833F", "jdbc:as400:172.20.1.41;prompt=false"), PRODUCCION("ERPLX833F", "jdbc:as400:172.20.1.41;prompt=false"), 
    QA("ERPLX833F", "jdbc:as400:172.20.1.131;prompt=false");
    
    private String esquema;
    private String urlConexion;
    
    private EsquemaAmbiente(String esquema, String urlConexion) {
        this.esquema = esquema;
        this.urlConexion = urlConexion;
    }

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        this.esquema = esquema;
    }

    public String getUrlConexion() {
        return urlConexion;
    }

    public void setUrlConexion(String urlConexion) {
        this.urlConexion = urlConexion;
    }
    
    
}
