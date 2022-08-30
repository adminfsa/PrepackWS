/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farmacapsulas.prepackws.service;

import com.farmacapsulas.prepackws.util.Conexion;
import com.farmacapsulas.prepackws.util.EsquemaAmbiente;
import com.farmacapsulas.prepackws.util.PrepackException;
import com.farmacapsulas.prepackws.util.UtilPrepack;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.log4j.Logger;


/**
 *
 * @author wsalgado
 */

public class PrepackService {
     private PropertiesBean propertiesBean;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private final static Logger LOGGER = Logger.getLogger("PREPACK");
    
    public PrepackService() {
    }
    
    public void actualizarDato(String sql) throws Exception{
        try(Conexion con = new Conexion();){
            con.conectarDB2();
            Statement st = con.crearStatement();
            st.execute(sql);
        }catch (Exception  e) {
            e.printStackTrace();
            throw(e);
        }
    }
    
    public boolean distribuirPrecios(int numeroOrden) throws Exception{
        boolean sw = false;
        if (validarPedidoPrepack(numeroOrden)){
            String esquema = UtilPrepack.PRODUCTIVE_SERVICE ? EsquemaAmbiente.PRODUCCION.getEsquema() : EsquemaAmbiente.PRUEBAS.getEsquema();
            try (final Conexion con = new Conexion()) {
                con.conectarDB2();
                propertiesBean = new PropertiesBean();
                String sql =  propertiesBean.getProperty("consultarLineasPedidoPrepack");
                PreparedStatement st = con.crearPreparedStatement(sql);
                st.setInt(1, numeroOrden);
                ResultSet rs = st.executeQuery();
                LOGGER.debug("PRCK-N2-DEB-PAS1-DistribuirPrecios("+numeroOrden+"): "+sql);

                String TIPO = "";
                int linea = 0;
                int linea2 = 0;
                String sq = "";
                double LLIST = 0;
                double LNET = 0;
                double LOVRP = 0;
                double LORPR = 0;
                double LBNET = 0;
                double LBLST = 0;
                double CLBBTL = 0;
                double CLNPBL = 0;
                double CLNPTL = 0;
                double CLSPTL = 0;
                double CLPBTS = 0;
                int Etapa = 1;
                int i = 1;
                double COSTOT = 0, COSTO = 0;
                double cost[] = new double[30];
                while (rs.next()) {
                    TIPO = rs.getString("LCPNT");
                    LOGGER.debug("PRCK-N2-DEB-PAS2-DistribuirPrecios("+numeroOrden+"): Linea:"+linea+" -> Valor i:"+i+" -> Tipo:"+TIPO+" -> Etapa:"+Etapa);
                    if (!TIPO.equals("Y") && Etapa == 2) {// se acabaron los hijos
                        // y viene un articulo
                        // normal
                        Etapa = 3;
                        LOGGER.debug("Linea:"+linea+" -> Valor i:"+i+" -> Tipo:"+TIPO+" -> Etapa:"+Etapa);
                    }

                    if (Etapa == 3) {
                        // System.out.print("etapa3->" + COSTOT );
                        // Valor costo total COSTOT

                        for (int k = 1; k < i; k++) {
                            String cad1 = (cost[k] / COSTOT * LNET) + "";
                            String cad2 = (cost[k] / COSTOT * LLIST) + ""; // + " /
                            // ECL.LQORD
                            // ";
                            String cad3 = (cost[k] / COSTOT * LOVRP) + ""; // + " /
                            // ECL.LQORD
                            // ";
                            String cad4 = (cost[k] / COSTOT * LORPR) + ""; // + " /
                            // ECL.LQORD
                            // ";
                            String cad5 = (cost[k] / COSTOT * LBNET) + ""; // + " /
                            // ECL.LQORD
                            // ";
                            String cad6 = (cost[k] / COSTOT * LBLST) + ""; // + " /
                            // ECL.LQORD
                            // ";
                            String cad7 = (cost[k] / COSTOT * CLBBTL) + ""; // + " /
                            // ECL.LQORD
                            // ";
                            String cad8 = (cost[k] / COSTOT * CLNPBL) + ""; // + " /
                            // ECL.LQORD
                            // ";
                            String cad9 = (cost[k] / COSTOT * CLNPTL) + ""; // + " /
                            // ECL.LQORD
                            // ";
                            String cad10 = (cost[k] / COSTOT * CLSPTL) + ""; // + "
                            // /
                            // ECL.LQORD
                            // ";
                            String cad11 = (cost[k] / COSTOT * CLPBTS) + ""; // + "
                            // /
                            // ECL.LQORD
                            // ";

                            int l = (linea + k);

                            sq = "UPDATE  " + esquema + ".ECL ECL SET LNET =  "
                                            + cad1;
                            sq = sq + " , LLIST 	= " + cad2;
                            sq = sq + " , LOVRP 	= " + cad3;
                            sq = sq + " , LORPR 	= " + cad4;
                            sq = sq + " , LBNET 	= " + cad5;
                            sq = sq + " , LBLST     = " + cad6;
                            sq = sq + " , CLBBTL	= " + cad7;
                            sq = sq + " , CLNPBL    = " + cad8;
                            sq = sq + " , CLNPTL    = " + cad9;
                            sq = sq + " , CLSPTL    = " + cad10;
                            sq = sq + " , CLPBTS    = " + cad11;
                            sq = sq + " WHERE ECL.LORD = " + numeroOrden;
                            sq = sq + " AND ECL.LLINE = " + l;

                            LOGGER.debug("Linea:"+l+" -> SQL:"+sq);
                            actualizarDato(sq);
                        }

                        sq = "UPDATE  " + esquema + ".ECL ECL SET LNET =0	";
                        sq = sq + " , LOVRP = 0 ";
                        sq = sq + " , LORPR = 0";
                        sq = sq + " , LBNET = 0";
                        sq = sq + " , LBLST = CAST( LNET AS DECIMAL(14,4))";
                        sq = sq + " , CLBBTL = 0 ";
                        sq = sq + " , CLNPBL = 0 ";
                        sq = sq + " , CLNPTL = 0 ";
                        sq = sq + " , clsptl = 0 ";
                        sq = sq + " , CLPBTS = 0 ";
                        sq = sq + " WHERE ECL.LORD = " + numeroOrden;
                        sq = sq + " AND ECL.LLINE = " + linea;

                        LOGGER.debug("Linea:"+linea+" -> SQL:"+sq);
                        //conex.EjecutarDML(sq);
                        actualizarDato(sq);
                        Etapa = 1;
                    }// FIN ETAPA3

                    if (Etapa == 1) {
                        // System.out.print("etapa1");

                        // lnet =r.getDouble("LNET") * r.getDouble("LQORD");
                        LLIST = rs.getDouble("LLIST") * rs.getDouble("LQORD");
                        LNET = rs.getDouble("LNET") * rs.getDouble("LQORD");
                        LOVRP = rs.getDouble("LOVRP") * rs.getDouble("LQORD");
                        LORPR = rs.getDouble("LORPR") * rs.getDouble("LQORD");
                        LBNET = rs.getDouble("LBNET") * rs.getDouble("LQORD");
                        LBLST = rs.getDouble("LBLST") * rs.getDouble("LQORD");
                        CLBBTL = rs.getDouble("CLBBTL") * rs.getDouble("LQORD");
                        CLNPBL = rs.getDouble("CLNPBL") * rs.getDouble("LQORD");
                        CLNPTL = rs.getDouble("CLNPTL") * rs.getDouble("LQORD");
                        CLSPTL = rs.getDouble("CLSPTL") * rs.getDouble("LQORD");
                        CLPBTS = rs.getDouble("CLPBTS") * rs.getDouble("LQORD");

                        linea = rs.getInt("LLINE"); // linea en donde encuentra el
                        // padre
                        TIPO = rs.getString("LCPNT");

                        if (TIPO.equals("P")) {
                            String SQ = "UPDATE " + esquema
                                            + ".PDA set DADSCO = 0 where DAORDR = " + numeroOrden
                                            + " and DALINE = " + linea;

                            LOGGER.debug("Linea:"+linea+" -> SQL:"+SQ);
                            //conex.EjecutarDML(SQ);
                            actualizarDato(SQ);

                            TIPO = "Y";
                            COSTOT = 0;
                            COSTO = 0;
                            COSTOT = 0;
                            i = 1;
                            cost = new double[30];
                            Etapa = 2;
                        }
                    } // FIN ETAPA 1
                    else if (Etapa == 2) {
                        // System.out.print("etapa2");

                        COSTO = rs.getDouble("ICSCST");
                        double can = rs.getDouble("LQORD");
                        cost[i] = COSTO;
                        linea2 = rs.getInt("LLINE");
                        COSTOT = COSTOT + cost[i] * can;
                        i = i + 1;
                    } // FIN ETAPA2
                } // FIN WHILE

                if (Etapa == 2) // si el ultimo articulo fue hijo
                {
                    Etapa = 3;
                    // System.out.print("etapa3->" + COSTOT );
                    // Valor costo total COSTOT

                    for (int k = 1; k < i; k++) {
                        // + " / ECL.LQORD ";
                        String cad1 = (cost[k] / COSTOT * LNET) + "";
                        String cad2 = (cost[k] / COSTOT * LLIST) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad3 = (cost[k] / COSTOT * LOVRP) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad4 = (cost[k] / COSTOT * LORPR) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad5 = (cost[k] / COSTOT * LBNET) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad6 = (cost[k] / COSTOT * LBLST) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad7 = (cost[k] / COSTOT * CLBBTL) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad8 = (cost[k] / COSTOT * CLNPBL) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad9 = (cost[k] / COSTOT * CLNPTL) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad10 = (cost[k] / COSTOT * CLSPTL) + ""; // + " /
                        // ECL.LQORD
                        // ";
                        String cad11 = (cost[k] / COSTOT * CLPBTS) + ""; // + " /
                        // ECL.LQORD
                        // ";

                        int l = (linea + k);

                        sq = "UPDATE  " + esquema + ".ECL ECL SET LNET =  " + cad1;
                        sq = sq + " , LLIST = " + cad2;
                        sq = sq + " , LOVRP = " + cad3;
                        sq = sq + " , LORPR = " + cad4;
                        sq = sq + " , LBNET = " + cad5;
                        sq = sq + " , LBLST = " + cad6;
                        sq = sq + " , CLBBTL = " + cad7;
                        sq = sq + " , CLNPBL = " + cad8;
                        sq = sq + " , CLNPTL = " + cad9;
                        sq = sq + " , CLSPTL = " + cad10;
                        sq = sq + " , CLPBTS = " + cad11;
                        sq = sq + " WHERE ECL.LORD = " + numeroOrden;
                        sq = sq + " AND ECL.LLINE = " + l;
                        System.out.print(sq);
                        LOGGER.debug("Linea:"+l+" -> SQL:"+sq);
                        //conex.EjecutarDML(sq);
                        actualizarDato(sq);   
                    }

                    sq = "UPDATE  " + esquema + ".ECL ECL SET LNET =0	";
                    sq = sq + " , LOVRP = 0 ";
                    sq = sq + " , LORPR = 0";
                    sq = sq + " , LBNET = 0";
                    sq = sq + " , LBLST = CAST( LNET AS DECIMAL(14,4))";
                    sq = sq + " , CLBBTL = 0 ";
                    sq = sq + " , CLNPBL = 0 ";
                    sq = sq + " , CLNPTL = 0 ";
                    sq = sq + " , clsptl = 0 ";
                    sq = sq + " , CLPBTS = 0 ";
                    sq = sq + " WHERE ECL.LORD = " + numeroOrden;
                    sq = sq + " AND ECL.LLINE = " + linea;
                    System.out.print(sq);
                    LOGGER.debug("Linea:"+linea+" -> SQL:"+sq);
                    //conex.EjecutarDML(sq);
                    actualizarDato(sq);
                    Etapa = 1;
                }
                
            }catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            sw = true;
        }else{
            throw new PrepackException(0, "El pedido no tiene articulos prepack");
        }
        return sw;
    }
    
    public boolean validarPedidoPrepack(int numeroOrden) throws Exception{
        boolean sw = false;
        LOGGER.debug("PRCK-N2-DEB-PAS1-validarPedidoPrepack("+numeroOrden+"): ");
        if (numeroOrden == 0){
            throw new PrepackException(2, "Numero de orden invalido");
        }
        try (final Conexion con = new Conexion()) {
            con.conectarDB2();
            propertiesBean = new PropertiesBean();
            String sql =  propertiesBean.getProperty("validarPedidoPrepack");
            PreparedStatement st = con.crearPreparedStatement(sql);
            st.setInt(1, numeroOrden);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                int num = rs.getInt("LINEAS_PREPACK");
                if (num > 0){
                    sw = true;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return sw;
    }
}
