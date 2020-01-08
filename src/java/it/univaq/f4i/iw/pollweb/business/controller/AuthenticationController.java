/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class AuthenticationController extends BaseController {

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email != null && password != null) {
            DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
            User user = dataLayer.getUserDAO().findByEmailAndPassword(email, password);
            if (user != null) {
                SecurityLayer.createSession(request, email, user.getId().intValue());
                response.sendRedirect("/pollweb");
            } else {
                throw new ServletException("Username o password errati");
            }
        } else {
            throw new ServletException("Parametri mancanti");
        }
    }
    
    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityLayer.disposeSession(request);
        response.sendRedirect("/pollweb");
    }
    
    private void action_authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
        Participant participant = dataLayer.getParticipantDAO().findByEmailAndPasswordAndSurveyId(email, password, surveyId);
        if (participant != null) {
            HttpSession session = SecurityLayer.createSession(request, "", 0);
            session.setAttribute("participantid", participant.getId());
            response.sendRedirect("compile-survey?survey=" + surveyId);
        } else {
            throw new ServletException("Credenziali errate");
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else if (request.getParameter("logout") != null) {
                action_logout(request, response);
            }else if (request.getParameter("authenticate") != null) {
                action_authenticate(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (IOException ex) {
            Logger.getLogger(SurveysController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
