/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import org.hibernate.Session;

/**
 *
 * @author vincenzo
 */
public abstract class DAO {
    protected final Session session;
    
    public DAO(Session session) {
        this.session = session;
    }
}