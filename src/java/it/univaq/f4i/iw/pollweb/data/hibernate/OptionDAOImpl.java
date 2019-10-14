/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.business.model.Option;
import it.univaq.f4i.iw.pollweb.data.dao.OptionDAO;
import it.univaq.f4i.iw.pollweb.data.dao.DAO;
import org.hibernate.Session;

/**
 *
 * @author vince
 */
class OptionDAOImpl extends DAO implements OptionDAO{
    
    public OptionDAOImpl(Session s) {
        super(s);
    }

    @Override
    public Option findByid(Long id) {
        return session.get(Option.class, id);
    }
    
    @Override
    public void saveOrUpdate(Option option) {
        session.beginTransaction();
        session.saveOrUpdate(option);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Option option) {
        session.beginTransaction();
        session.delete(option);
        session.getTransaction().commit();
    }
}
