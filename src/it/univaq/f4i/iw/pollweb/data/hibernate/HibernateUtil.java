/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.business.model.ChoiceQuestion;
import it.univaq.f4i.iw.pollweb.business.model.DateQuestion;
import it.univaq.f4i.iw.pollweb.business.model.NumberQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Option;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.ReservedSurvey;
import it.univaq.f4i.iw.pollweb.business.model.Role;
import it.univaq.f4i.iw.pollweb.business.model.ShortTextQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.TextQuestion;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.ParticipantDAO;
import it.univaq.f4i.iw.pollweb.data.dao.SurveyDAO;
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
        SurveyDAO sd = dl.getSurveyDAO();
        UserDAO ud = dl.getUserDAO();
        ParticipantDAO pd = dl.getParticipantDAO();
        
        Survey s = new Survey();
        s.setOpeningText("Testo apertura sondaggio 1");
        s.setClosingText("Testo chiusura sondaggio 1");
        s.setActive(true);
        s.setTitle("Sondaggio");
        
        TextQuestion tq = new TextQuestion();
        tq.setCode("tq-1");
        tq.setText("Come ti chiami?");
        s.addQuestion(tq);
        
        ChoiceQuestion cq = new ChoiceQuestion();
        cq.setCode("cq-1");
        cq.setText("Che animale preferisci?");
        cq.addOption(new Option("Cane"));
        cq.addOption(new Option("Gatto"));
        s.addQuestion(cq);
        
        TextQuestion tq1 = new ShortTextQuestion();
        tq1.setText("Che università frequenti?");
        tq1.setCode("tq-2");
        s.addQuestion(tq1);
        
        ChoiceQuestion cq1 = new ChoiceQuestion();
        cq1.setCode("cq-2");
        cq1.setText("Ti piacciono i film horror?");
        cq1.addOption(new Option("si"));
        cq1.addOption(new Option("no"));
        cq1.setMaxNumberOfChoices(1);
        s.addQuestion(cq1);
        
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
        p1.setEmail("lauradegidio@gmail.com");
        p1.setPassword("password");
        p1.setName("Laura");
        p1.setSurname("D'Egidio");
        p1.setReservedSurvey(rs);
        
        User u = new User(); 
        u.setEmail("vincenzodepetris@gmail.com");
        u.setPassword("password");
        u.setRole(Role.RESPONSIBLE);
        u.setSurname("De Petris");
        u.setName("Vincenzo");
        s.setManager(u);
        rs.setManager(u);
        ud.saveOrUpdate(u);
        
        User u1 = new User();
        u1.setEmail("andreapagliarini@gmail.com");
        u1.setRole(Role.ADMINISTRATOR);
        u1.setPassword("password");
        u1.setName("Andrea");
        u1.setSurname("Pagliarini");
        ud.saveOrUpdate(u1);
        
        sd.saveOrUpdate(s);
        sd.saveOrUpdate(rs);
        pd.saveOrUpdate(p);
        pd.saveOrUpdate(p1);
    }
}