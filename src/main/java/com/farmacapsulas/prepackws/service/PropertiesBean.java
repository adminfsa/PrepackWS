/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farmacapsulas.prepackws.service;


import com.farmacapsulas.prepackws.util.UtilPrepack;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author wsalgado
 */
//@Singleton
//@Startup
public class PropertiesBean {
    private Properties properties;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /*@PostConstruct
    private void startup() {
        this.properties = loadQueries();
    }*/
    
    public PropertiesBean() {
        this.properties = loadQueries();
    }
   
    private Properties loadQueries(){
       Properties properties= new Properties();
       try {
           InputStream xmlStream = getClass().getResourceAsStream("/queries.xml");
           if( xmlStream == null ) {
               throw new NullPointerException();
           }
           else{
               properties.loadFromXML(xmlStream);
           }
       } catch (IOException exception) {
           exception.printStackTrace();
       }
       return properties;
   }
    
    public String getProperty(String name) {
        if (UtilPrepack.PRODUCTIVE_SERVICE){
            return properties.getProperty(name);
        }else{
            return properties.getProperty(name).replaceAll("ERPLXFU.", "FACLXFU.").replaceAll("ERPLX833F.", "PLOLX833F.").replaceAll("PEDIDO\\.", "PEDIDOTST.");
            //return properties.getProperty(name).replaceAll("ERPLXFU.", "FACLXFU.");
            //return properties.getProperty(name).replaceAll("ERPLXFU.", "FACLXFU.").replaceAll("ERPLX833F.", "FACLX833F.").replaceAll("PEDIDO\\.", "PEDIDOTST.");
            //return properties.getProperty(name).replaceAll("ERPLXFU.", "FACLXFU.").replaceAll("ERPLX833F.", "TSTLX833F.");
        }
    }
    
    /*
    @PreDestroy
    private void shutdown() {
        System.out.println("In PropertiesBean(Singleton)::shutdown()");
    }*/
}
