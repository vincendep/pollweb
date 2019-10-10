/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// TODO Filter
/**
 *
 * @author vincenzo
 */
public class AuthorizationController extends BaseController {

    private void action_authenticate(HttpServletRequest request, HttpServletResponse response, Long surveyId) throws IOException {
        DataLayer datalayer = (DataLayer) request.getAttribute("datalayer");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email != null && password != null && surveyId != null) {
            Participant participant = datalayer.getParticipantDAO().findByEmailAndPasswordAndSurveyId(email, password, surveyId);
            if (participant != null) {
                request.getSession().setAttribute("auth_participant", participant.getId());
                response.sendRedirect("/pollweb/surveys/" + surveyId);
            } else {
                request.setAttribute("message", "Cannot authenticate participant for this survey");
                action_error(request, response);
            }
        } else {
            request.setAttribute("message", "Missing params");
            action_error(request, response);
        }
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) {
        
    }   
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("survey") != null) {
                Long surveyId = Long.valueOf(request.getParameter("survey"));
                
                if (request.getParameter("authentication") != null) {
                    action_authenticate(request, response, surveyId);
                } 
                
                
                action_default(request, response);
            } else {
                request.setAttribute("message", "Missing param");
                action_error(request, response);
            }
        } catch (IOException | NumberFormatException e) {
            request.setAttribute("exception", e);
            action_error(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "AuthorizationController";
    }
    
    
    
}
