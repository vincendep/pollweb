/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.ReservedSurvey;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author vince
 */
public class ParticipantsController extends BaseController {

    private void action_add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idSurvey = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(idSurvey);
        Participant participant = new Participant();
        participant.setEmail(request.getParameter("email"));
        participant.setPassword(request.getParameter("password"));
        participant.setReservedSurvey((ReservedSurvey) survey);
        ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().saveOrUpdate(participant);
        response.sendRedirect("account/survey-details?n=" + idSurvey);
    }
    
    private void action_add_from_file(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // TODO read from csv file
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idSurvey = SecurityLayer.checkNumeric(request.getParameter("survey"));
        int participantId = SecurityLayer.checkNumeric(request.getParameter("n"));
        Participant participant = ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().findById(participantId);
        ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().delete(participant);
        response.sendRedirect("account/survey-details?n=" + idSurvey);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try  {
            if (request.getParameter("add") != null) {
                action_add(request, response);
            } else if (request.getParameter("add-from-file") != null) {
                action_add_from_file(request, response);
            } else if (request.getParameter("delete") != null) {
                action_delete(request, response);
            } else {
                action_error(request, response);
            }
        } catch (Exception e) {
          throw new ServletException(e);
        }
    }
    
}
