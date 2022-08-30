/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  com.farmacapsulas.prepackws.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author wsalgado
 */
public class Conexion implements AutoCloseable{
    private Properties properties;
    public Conexion(){
        this.properties = loadQueries();
    }

    public void commit() throws Exception{
        conexion.commit();
    }
    
     private Properties loadQueries(){
        Properties properties= new Properties();
        try {
            InputStream xmlStream = getClass().getResourceAsStream("/datosconexion.xml");
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

    public Connection getConexion() throws Exception {
        return conexion;
    }

    public void autoCommit( boolean commit )throws Exception {
        conexion.setAutoCommit(commit);
    }

    public void conectar(String jndi) throws Exception {
        try
        {
            Context ic = null;
            ic = new InitialContext();
            DataSource ds = (DataSource)ic.lookup( jndi );
            conexion = ds.getConnection();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new Exception("No se pude establecer la conexion con la base de datos ");
        }
    }
    
    public void conectarDB2()throws Exception{
        String  urlConexion = this.properties.getProperty("urlConexion"); 
        String driver = this.properties.getProperty("driver"); 
        String usuario =  UtilPrepack.PRODUCTIVE_SERVICE? this.properties.getProperty("usuario") : this.properties.getProperty("usuarioTest"); 
        String password = UtilPrepack.PRODUCTIVE_SERVICE? this.properties.getProperty("password") : this.properties.getProperty("passwordTest"); 
        this.conectar(driver, urlConexion, usuario, password);
        //System.out.println("Conexion a DB Realizada");
    }
            
    
    public void conectar( String driver, String urlConexion, String usuario, String password ) throws Exception{
        this.driver = driver;
        this.urlConexion = urlConexion;
        this.usuario = usuario;
        this.password = password;
        try { 
        //oracle.jdbc.driver.OracleDriver
            Class.forName( driver ); 
            conexion = DriverManager.getConnection(urlConexion,usuario,password);  
        } catch (Exception e) { 
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            throw new Exception("No se pude establecer la conexion con la base de datos " + e.toString() + " \n - "+ exceptionAsString);
        }  
    }
    
    @Override
    public void close() throws Exception {
        desconectar();
    }

    public void desconectar() throws Exception {
        if( conexion != null ){
            try {
                conexion.close();
                //System.out.println("Conexion cerrada");
            }
            catch(SQLException e) {
                e.printStackTrace();
                throw new Exception("Error al desconectar la base de datos");
            }
        }
    }

    public void setUrlConexion(String urlConexion) {
        this.urlConexion = urlConexion;
    }

    public String getUrlConexion() {
        return urlConexion;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public Statement crearStatement() throws Exception {
        try {
            Statement statement = conexion.createStatement();
            return statement;
        }
        catch(SQLException e) {
            throw new Exception("Error en la base de datos");
        }
    }
    
    public ResultSet ejecutarQuery(String SQL)throws Exception {
        ResultSet re  = null;
        try {
            Statement st = conexion.createStatement();
            re = st.executeQuery(SQL);
            return re;
        } catch (Exception e) {
            System.out.println("Mensaje de Error: " + e.getMessage());
            throw (e);
        } 
    }

    public PreparedStatement crearPreparedStatement(String sql) throws Exception {
        try {
            PreparedStatement preparedstatement = conexion.prepareStatement(sql);
            return preparedstatement;
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        throw new Exception("Error en la base de datos");
    }

    public PreparedStatement prepareStatement(String sql) throws Exception {
        try {
            PreparedStatement preparedstatement = conexion.prepareStatement(sql);
            return preparedstatement;
        }
        catch(SQLException e) {
            throw new Exception("Error en la base de datos");
        }
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }

    String driver;
    String urlConexion;
    String usuario;
    String password;
    Connection conexion;

    
}
