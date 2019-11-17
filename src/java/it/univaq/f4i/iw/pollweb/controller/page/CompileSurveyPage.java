/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.controller.page;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.controller.BaseController;
import it.univaq.f4i.iw.pollweb.business.model.*;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vincenzo
 */
public class CompileSurveyPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("survey") == null) {
                throw new ServletException("Parametro survey mancante");
            }
            long surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
            Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
            if (survey == null) {
                throw new ServletException("Parametro survey invalido");
            }
            if (!survey.isActive()) {
                throw new ServletException("Il sondaggio è chiuso");
            }
            if (survey instanceof ReservedSurvey) {
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("participantid") != null) {
                    DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
                    long participantId = ((Long) session.getAttribute("participantid"));
                    Participant participant = dataLayer.getParticipantDAO().findById(participantId);
                    if (participant.getReservedSurvey().equals(survey)) {
                        if (participant.hasAlreadySubmitted()) {
                            session.removeAttribute("participantid");
                            throw new ServletException("Hai già compilato questo sondaggio");
                        } else {
                            request.setAttribute("survey", survey);
                            request.setAttribute("page_title", "Compile survey page");
                            TemplateResult res = new TemplateResult(getServletContext());
                            res.activate("compile-survey.ftlh", request, response);
                        }
                    } else {
                        session.removeAttribute("participantid");
                        response.sendRedirect("authentication?survey=" + surveyId);
                    }
                } else {
                    response.sendRedirect("authentication?survey=" + surveyId);
                }
            } else {
                request.setAttribute("survey", survey);
                request.setAttribute("page_title", "Compile survey page");
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("compile-survey.ftlh", request, response);
            }
        } catch (TemplateManagerException | NumberFormatException | IOException e) {
            throw new ServletException(e);
        } 
    }
}

