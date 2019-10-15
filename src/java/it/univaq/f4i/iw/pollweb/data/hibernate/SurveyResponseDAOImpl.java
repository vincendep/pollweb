/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import it.univaq.f4i.iw.pollweb.data.dao.SurveyResponseDAO;
import it.univaq.f4i.iw.pollweb.data.dao.DAO;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author vince
 */
class SurveyResponseDAOImpl extends DAO implements SurveyResponseDAO {
    
    public SurveyResponseDAOImpl(Session s) {
        super(s);
    }

    @Override
    public SurveyResponse findById(long id) {
        return session.get(SurveyResponse.class, id);
    }

    @Override
    public List<SurveyResponse> findBySurvey(Survey survey) {
        String hql = "FROM SurveyResponse WHERE survey = :survey";
        Query<SurveyResponse> query = session.createQuery(hql);
        query.setParameter("survey", survey);
        return query.getResultList();
    }

    @Override
    public void saveOrUpdate(SurveyResponse sr) {
        this.session.saveOrUpdate(sr);
    }

    @Override
    public void delete(SurveyResponse sr) {
        session.delete(sr);
    }
    
}
