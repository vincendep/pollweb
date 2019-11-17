/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.data.dao.DAO;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.data.dao.ParticipantDAO;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author vince
 */
class ParticipantDAOImpl extends DAO implements ParticipantDAO {
    
    public ParticipantDAOImpl(Session s) {
        super(s);
    }

    @Override
    public Participant findById(long id) {
        return session.get(Participant.class, id);
    }
    
    @Override
    public Participant findByEmailAndPasswordAndSurveyId(String email, String password, long surveyId) {
        String hql = "FROM Participant WHERE email = :email AND password = :password AND id_survey = :surveyId";
        Query<Participant> query = session.createQuery(hql);
        query.setParameter("email", email);
        query.setParameter("password", password);
        query.setParameter("surveyId", surveyId);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void saveOrUpdate(Participant participant) {
        session.saveOrUpdate(participant);
    }

    @Override
    public void delete(Participant participant) {
        session.delete(participant);
    }  
}
