/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;


import it.univaq.f4i.iw.pollweb.business.model.Answer;
import it.univaq.f4i.iw.pollweb.business.model.Question;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.Option;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import org.hibernate.Session;

/**
 *
 * @author vince
 */
public final class HibernateDataLayer extends DataLayer {

    private final Session session;
    
    public HibernateDataLayer() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        registerDAO(Survey.class, new SurveyDAOImpl(this.session));
        registerDAO(Answer.class, new AnswerDAOImpl(this.session));
        registerDAO(Question.class, new QuestionDAOImpl(this.session));
        registerDAO(SurveyResponse.class, new SurveyResponseDAOImpl(this.session));
        registerDAO(User.class, new UserDAOImpl(this.session));
        registerDAO(Participant.class, new ParticipantDAOImpl(this.session));
        registerDAO(Option.class, new OptionDAOImpl(this.session));
    }
    
    public Session getSession() {
        return this.session;
    }

    @Override
    public void init() {
        this.session.beginTransaction();
    }
    
    @Override
    public void destroy() {
        this.session.flush();
        this.session.getTransaction().commit();
        this.session.close();
    }
}
