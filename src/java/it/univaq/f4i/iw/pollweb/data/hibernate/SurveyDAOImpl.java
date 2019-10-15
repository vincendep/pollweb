/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.data.dao.DAO;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.SurveyDAO;
import java.util.List;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

/**
 *
 * @author vince
 */
class SurveyDAOImpl extends DAO implements SurveyDAO {
    
    public SurveyDAOImpl(Session s) {
        super(s);
    }

    @Override
    public Survey findById(long id) {
        return session.get(Survey.class, id);
    }

    @Override
    public List<Survey> findByManager(User manager) {
        String hql = "FROM Survey WHERE manager = :manager";
        TypedQuery<Survey> query = session.createQuery(hql);
        query.setParameter("manager", manager);
        return query.getResultList();
    }

    @Override
    public List<Survey> findByActive(boolean active) {
        String hql = "FROM Survey WHERE active = :active";
        TypedQuery<Survey> query = session.createQuery(hql);
        query.setParameter("active", active);
        return query.getResultList();
    }

    @Override
    public List<Survey> findAllReserved() {
        String hql = "FROM ReservedSurvey";
        TypedQuery<Survey> query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public List<Survey> findAllNotReserved() {
        String hql = "FROM Survey WHERE survey_type='public'";
        TypedQuery<Survey> query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public List<Survey> findAll() {
        String hql = "FROM Survey";
        TypedQuery<Survey> query = session.createQuery(hql);
        return query.getResultList();
    }

    @Override
    public void saveOrUpdate(Survey survey) {
        session.saveOrUpdate(survey);
    }

    @Override
    public void delete(Survey survey) {
        session.delete(survey);
    }
    
}
