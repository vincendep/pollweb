/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.business.model.Answer;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import it.univaq.f4i.iw.pollweb.data.dao.AnswerDAO;
import it.univaq.f4i.iw.pollweb.data.dao.DAO;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author vince
 */
class AnswerDAOImpl extends DAO implements AnswerDAO {

    public AnswerDAOImpl(Session session) {
        super(session);
    }

    @Override
    public Answer findById(Long id) {
        return session.get(Answer.class, id);
    }
    
    @Override
    public void saveOrUpdate(Answer answer) {
        session.beginTransaction();
        session.saveOrUpdate(answer);
        session.getTransaction().commit();
    }

    @Override
    public void delete(Answer answer) {
        session.beginTransaction();
        session.delete(answer);
        session.getTransaction().commit();
    }
    
    
    
}
