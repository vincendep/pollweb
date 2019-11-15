/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.ReservedSurvey;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class Utility {
    
    public static boolean userCanSubmitSurvey(HttpServletRequest request) {
        return false;
    }
    
    public static boolean userManageSurvey(HttpServletRequest request) {
        HttpSession session = SecurityLayer.checkSession(request);
        if (session == null) {
            return false;
        }
        DataLayer dl = (DataLayer) request.getAttribute("datalayer");
        Survey survey = dl.getSurveyDAO().findById(SecurityLayer.checkNumeric(request.getParameter("survey")));
        if (survey == null) {
            return false;
        }
        return session.getAttribute("userid").equals(survey.getManager().getId().intValue());
    }
}
