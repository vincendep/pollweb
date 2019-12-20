/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.business.model.ChoiceAnswer;
import it.univaq.f4i.iw.pollweb.business.model.ChoiceQuestion;
import it.univaq.f4i.iw.pollweb.business.model.DateQuestion;
import it.univaq.f4i.iw.pollweb.business.model.NumberQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Option;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.ReservedSurvey;
import it.univaq.f4i.iw.pollweb.business.model.Role;
import it.univaq.f4i.iw.pollweb.business.model.ShortTextAnswer;
import it.univaq.f4i.iw.pollweb.business.model.ShortTextQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import it.univaq.f4i.iw.pollweb.business.model.TextAnswer;
import it.univaq.f4i.iw.pollweb.business.model.TextQuestion;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.ParticipantDAO;
import it.univaq.f4i.iw.pollweb.data.dao.SurveyDAO;
import it.univaq.f4i.iw.pollweb.data.dao.SurveyResponseDAO;
import it.univaq.f4i.iw.pollweb.data.dao.UserDAO;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author vincenzo
 */
public class HibernateUtil implements ServletContextListener {
    
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sessionFactory = new Configuration()
                .configure("/it/univaq/f4i/iw/pollweb/data/hibernate/hibernate.cfg.xml")
                .buildSessionFactory();
        
        populateDB();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sessionFactory.close();
    }
    
    private void populateDB() {
        // test data
        HibernateDataLayer dl = new HibernateDataLayer();
        dl.getSession().beginTransaction();
        SurveyDAO sd = dl.getSurveyDAO();
        UserDAO ud = dl.getUserDAO();
        ParticipantDAO pd = dl.getParticipantDAO();
        SurveyResponseDAO srd = dl.getSurveyResponseDAO();
        
        Survey s = new Survey();
        s.setOpeningText("Testo apertura sondaggio 1");
        s.setClosingText("Testo chiusura sondaggio 1");
        s.setActive(true);
        s.setTitle("Sondaggio");
        SurveyResponse sr = new SurveyResponse();
        sr.setSurvey(s);
        s.addSurveyResponse(sr);
        
        TextQuestion tq = new TextQuestion();
        tq.setCode("tq-1");
        tq.setText("Come ti chiami?");
        s.addQuestion(tq);
        TextAnswer ta = new TextAnswer();
        ta.setQuestion(tq);
        tq.addAnswer(ta);
        ta.setAnswer("Vincenzo");
        sr.addAnswer(ta);
        
        ChoiceQuestion cq = new ChoiceQuestion();
        cq.setCode("cq-1");
        cq.setText("Che animale preferisci?");
        Option opt1 = new Option("Cane");
        Option opt2 = new Option("Gatto");
        cq.addOption(opt1);
        cq.addOption(opt2);
        s.addQuestion(cq);
        ChoiceAnswer ca = new ChoiceAnswer();
        ca.setQuestion(cq);
        cq.addAnswer(ca);
        ca.addOption(opt1);
        sr.addAnswer(ca);
        
        TextQuestion tq1 = new ShortTextQuestion();
        tq1.setText("Che università frequenti?");
        tq1.setCode("tq-2");
        s.addQuestion(tq1);
        ShortTextAnswer ta1 = new ShortTextAnswer();
        ta1.setQuestion(tq1);
        tq1.addAnswer(ta1);
        ta1.setAnswer("Univaq");
        sr.addAnswer(ta1);
        
        ChoiceQuestion cq1 = new ChoiceQuestion();
        cq1.setCode("cq-2");
        cq1.setText("Ti piacciono i film horror?");
        Option o = new Option("si");
        Option o2 = new Option("no");
        cq1.addOption(o);
        cq1.addOption(o2);
        cq1.setMaxNumberOfChoices(1);
        s.addQuestion(cq1);
        ChoiceAnswer ca1 = new ChoiceAnswer();
        ca1.setQuestion(cq1);
        cq1.addAnswer(ca1);
        ca1.addOption(o);
        sr.addAnswer(ca1);
        
        ReservedSurvey rs = new ReservedSurvey();
        rs.setTitle("Sondaggio riservato");
        rs.setOpeningText("Testo apertura sondaggio riservato");
        rs.setClosingText("Testo chiusura sondaggio riservato");
        rs.setActive(true);
        
        NumberQuestion nq = new NumberQuestion();
        nq.setCode("nq-1");
        nq.setMaxValue(100);
        nq.setMinValue(0);
        nq.setText("Quanti anni hai?");
        rs.addQuestion(nq);
        
        DateQuestion dq = new DateQuestion();
        dq.setCode("dq-1");
        dq.setMandatory(true);
        dq.setText("In che giorno è nato Napoleone?");
        rs.addQuestion(dq);
        
        Participant p = new Participant();
        p.setEmail("vincenzodepetris@gmail.com");
        p.setPassword("password");
        p.setName("Vincenzo");
        p.setSurname("De Petris");
        p.setReservedSurvey(rs);
        
        Participant p1 = new Participant();
        p1.setEmail("lauradiegidio@gmail.com");
        p1.setPassword("password");
        p1.setName("Laura");
        p1.setSurname("Di Egidio");
        p1.setReservedSurvey(rs);
        
        User u = new User(); 
        u.setEmail("vincenzodepetris@gmail.com");
        u.setPassword("password");
        u.setRole(Role.ADMINISTRATOR);
        u.setSurname("De Petris");
        u.setName("Vincenzo");
        s.setManager(u);
        rs.setManager(u);
        ud.saveOrUpdate(u);
        
        User u1 = new User();
        u1.setEmail("lauradiegidio@gmail.com");
        u1.setRole(Role.ADMINISTRATOR);
        u1.setPassword("password");
        u1.setName("Laura");
        u1.setSurname("Di Egidio");
        ud.saveOrUpdate(u1);
        
        User u2 = new User();
        u2.setEmail("denisdipatrizio@gmail.com");
        u2.setRole(Role.ADMINISTRATOR);
        u2.setPassword("password");
        u2.setName("Denis");
        u2.setSurname("Di Patrizio");
        ud.saveOrUpdate(u2);
        
        sd.saveOrUpdate(s);
        sd.saveOrUpdate(rs);
        srd.saveOrUpdate(sr);
        pd.saveOrUpdate(p);
        pd.saveOrUpdate(p1);
        
        dl.getSession().flush();
        dl.getSession().getTransaction().commit();
        dl.getSession().close();
    }
}