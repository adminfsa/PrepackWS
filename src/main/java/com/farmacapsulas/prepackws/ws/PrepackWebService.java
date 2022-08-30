/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farmacapsulas.prepackws.ws;

import com.farmacapsulas.prepackws.entities.Message;
import com.farmacapsulas.prepackws.entities.RequestDP;
import com.farmacapsulas.prepackws.entities.ResponseDP;
import com.farmacapsulas.prepackws.service.PrepackService;
import com.farmacapsulas.prepackws.util.PrepackException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author wsalgado
 */
@WebService(serviceName = "PrepackWebService")
public class PrepackWebService {
    PrepackService prepackService; 
    /**
     * This is a sample web service operation
     */
    
    
    
    public PrepackWebService() {
        
    }

    @WebMethod(operationName = "distribuirPrecios")
    public ResponseDP distribuirPrecios(@WebParam(name = "RequestDP") RequestDP requestDP) {
        Message message = new Message(0, "");
        ResponseDP responseDP = null;
        boolean sw = false;
        try {
            prepackService = new PrepackService();
            prepackService.distribuirPrecios(requestDP.getNumeroOrden());
            message.setCodigoError(0);
            message.setMensajeError("Proceso exitoso");
            sw = true;
        }catch (PrepackException ex) { 
            message.setCodigoError(2);
            message.setMensajeError(ex.getMensajeError());
            sw = false;
        }catch (Exception ex) {
            Logger.getLogger(PrepackWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        responseDP = new ResponseDP(sw, message);
        return responseDP;
    }
    
    @WebMethod(operationName = "validarPedidoPrepack")
    public ResponseDP validarPedidoPrepack(@WebParam(name = "RequestDP") RequestDP requestDP){
        Message message = new Message(0, "");
        ResponseDP responseDP = null;
        boolean sw = false;
        if (requestDP != null && requestDP.getNumeroOrden() > 0 
                && requestDP.getUsuario() != null && requestDP.getUsuario().trim().length() > 0){
            try {
                prepackService = new PrepackService();
                sw = prepackService.validarPedidoPrepack(requestDP.getNumeroOrden());
                if (sw){
                    message.setMensajeError("Pedido Prepack valido para distribucion de precios");
                }else{
                    message.setMensajeError("Este pedido no es valido para distribucion de precios Prepack");
                }
            }catch (PrepackException ex) { 
                message.setCodigoError(2);
                message.setMensajeError(ex.getMensajeError());
                sw = false;
            }catch (Exception ex) {
                message.setCodigoError(1);
                message.setMensajeError(ex.getMessage());
                Logger.getLogger(PrepackWebService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            message.setCodigoError(2);
            message.setMensajeError("Peticion invalida, numero de orden y usuario son obligatorios");
            sw = false;
        }
        responseDP = new ResponseDP(sw, message);
        return responseDP;
    }
}
