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
public class ResponseDP {
    private boolean realizado;
    private Message message;

    public ResponseDP() {
    }
    
    public ResponseDP(boolean realizado, Message message) {
        this.realizado = realizado;
        this.message = message;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
    
    
}
