/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import it.univaq.f4i.iw.pollweb.business.model.Answer;
import it.univaq.f4i.iw.pollweb.business.model.Option;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.Question;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import it.univaq.f4i.iw.pollweb.business.model.User;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vincenzo
 */
public abstract class DataLayer {
    
    private final Map<Class, DAO> daos = new HashMap<>();
    
    public abstract void init();
    
    public abstract void destroy();
    
    protected void registerDAO(Class c, DAO d) {
        this.daos.put(c, d);
    }
    
    public SurveyDAO getSurveyDAO () {
        return (SurveyDAO) daos.get(Survey.class);
    }
    
    public SurveyResponseDAO getSurveyResponseDAO () {
        return (SurveyResponseDAO) daos.get(SurveyResponse.class);
    }
    
    public AnswerDAO getAnswerDAO () {
        return (AnswerDAO) daos.get(Answer.class);
    }
    
    public QuestionDAO getQuestionDAO() {
        return (QuestionDAO) daos.get(Question.class);
    }
    
    public UserDAO getUserDAO() {
        return (UserDAO) daos.get(User.class);
    }
    
    public ParticipantDAO getParticipantDAO() {
        return (ParticipantDAO) daos.get(Participant.class);
    }
    
    public OptionDAO getOptionDAO() {
        return (OptionDAO) daos.get(Option.class);
    }
}
