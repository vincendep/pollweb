/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.ReservedSurvey;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import it.univaq.f4i.iw.pollweb.business.utility.FormUtility;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class CompileSurveyController extends BaseController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
            Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
            if (! survey.isActive()) {
                throw new ServletException("Il sondaggio è chiuso");
            }
            if (survey instanceof ReservedSurvey) {
                HttpSession session = request.getSession(false);
                ReservedSurvey reservedSurvey = (ReservedSurvey) survey;
                long participantId = (Long) session.getAttribute("participantid");
                Participant participant = ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().findById(participantId);
                if ( (! participant.hasAlreadySubmitted()) && participant.getReservedSurvey().equals(reservedSurvey)) {
                    SurveyResponse surveyResponse = FormUtility.createSurveyResponseModel(request, reservedSurvey);
                    if (surveyResponse.isValid()) {
                        ((DataLayer) request.getAttribute("datalayer")).getSurveyResponseDAO().saveOrUpdate(surveyResponse);
                        participant.setSubmitted(true);
                        session.removeAttribute("participantid");
                        TemplateResult res = new TemplateResult(getServletContext());
                        request.setAttribute("page_title", "Confirm compilation page");
                        request.setAttribute("survey", survey);
                        res.activate("compilation-confirm.ftlh", request, response);
                    } else {
                        throw new ServletException("La compilazione non è valida");
                    }
                } else {
                    session.removeAttribute("participantid");
                    throw new ServletException("Non puoi compilare questo sondaggio");
                }
            } else {
                SurveyResponse surveyResponse = FormUtility.createSurveyResponseModel(request, survey);
                if (surveyResponse.isValid()) {
                    ((DataLayer) request.getAttribute("datalayer")).getSurveyResponseDAO().saveOrUpdate(surveyResponse);
                    TemplateResult res = new TemplateResult(getServletContext());
                    request.setAttribute("page_title", "Confirm compilation page");
                    request.setAttribute("survey", survey);
                    res.activate("compilation-confirm.ftlh", request, response);
                } else {
                    throw new ServletException("La compilazione non è valida");
                }
            }
        } catch (NumberFormatException | TemplateManagerException ex) {
            throw new ServletException(ex);
        } 
    }
}
