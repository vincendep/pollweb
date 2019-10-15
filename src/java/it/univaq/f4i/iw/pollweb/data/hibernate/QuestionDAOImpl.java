/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.business.model.Question;
import it.univaq.f4i.iw.pollweb.data.dao.QuestionDAO;
import it.univaq.f4i.iw.pollweb.data.dao.DAO;
import org.hibernate.Session;

/**
 *
 * @author vince
 */
class QuestionDAOImpl extends DAO implements QuestionDAO {
        
    public QuestionDAOImpl(Session s) {
        super(s);
    }

    @Override
    public Question findById(long id) {
        return session.get(Question.class, id);
    }

    @Override
    public void saveOrUpdate(Question question) {
        session.saveOrUpdate(question);
    }

    @Override
    public void delete(Question question) {
        session.delete(question);
    }
    
}
