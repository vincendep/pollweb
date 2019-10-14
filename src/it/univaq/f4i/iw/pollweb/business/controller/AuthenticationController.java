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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vince
 */
public class AuthenticationController extends BaseController {

    private void action_authenticate(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Long surveyId = (Long) request.getAttribute("survey");
        if (email != null && password != null && surveyId != null) {
            DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
            Participant participant = dataLayer.getParticipantDAO().findByEmailAndPasswordAndSurveyId(email, password, surveyId);
            if (participant != null) {
                request.getSession().setAttribute("auth_participant", participant.getId());
                response.sendRedirect("/surveys?id=" + surveyId);
            } else {
                request.setAttribute("message", "Failed authentication");
                action_error(request, response);
            }
        } else {
            request.setAttribute("message", "Missing params");
            action_error(request, response);
        }
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email != null && password != null) {
            DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
            User user = dataLayer.getUserDAO().findByEmailAndPassword(email, password);
            if (user != null) {
                SecurityLayer.createSession(request, email, user.getId().intValue());
                response.sendRedirect("/pollweb");
            } else {
                request.setAttribute("message", "Username o password errati");
                action_error(request, response);
            }
        } else {
            request.setAttribute("message", "400 BAD REQUEST");
            action_error(request, response);
        }
    }
    
    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityLayer.disposeSession(request);
        response.sendRedirect("/pollweb");
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else if (request.getParameter("logout") != null) {
                action_logout(request, response);
            } else if (request.getAttribute("authentication") != null) {
                action_authenticate(request, response);
            }
        } catch ( IOException | NumberFormatException  ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
}
