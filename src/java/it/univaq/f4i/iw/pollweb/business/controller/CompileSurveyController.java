/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Answer;
import it.univaq.f4i.iw.pollweb.business.model.ChoiceAnswer;
import it.univaq.f4i.iw.pollweb.business.model.ChoiceQuestion;
import it.univaq.f4i.iw.pollweb.business.model.DateAnswer;
import it.univaq.f4i.iw.pollweb.business.model.DateQuestion;
import it.univaq.f4i.iw.pollweb.business.model.NumberAnswer;
import it.univaq.f4i.iw.pollweb.business.model.NumberQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Participant;
import it.univaq.f4i.iw.pollweb.business.model.Question;
import it.univaq.f4i.iw.pollweb.business.model.ReservedSurvey;
import it.univaq.f4i.iw.pollweb.business.model.ShortTextAnswer;
import it.univaq.f4i.iw.pollweb.business.model.ShortTextQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import it.univaq.f4i.iw.pollweb.business.model.TextAnswer;
import it.univaq.f4i.iw.pollweb.business.model.TextQuestion;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import java.time.LocalDate;
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
                    SurveyResponse surveyResponse = createSurveyResponseFromRequest(request, reservedSurvey);
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
                SurveyResponse surveyResponse = createSurveyResponseFromRequest(request, survey);
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
    
    private SurveyResponse createSurveyResponseFromRequest(HttpServletRequest request, Survey survey) {
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setSurvey(survey);
        for (Question q: survey.getQuestions()) {
            if (request.getParameter(q.getCode()) != null) {
                Answer answer = null;
                if (q instanceof ChoiceQuestion) {
                    ChoiceAnswer ca = new ChoiceAnswer();
                    for (String index: request.getParameterValues(q.getCode())) {
                        ca.addOption(((ChoiceQuestion) q).getOption(Integer.valueOf(index)));
                    }
                    answer = ca;
                } else if (q instanceof DateQuestion) {
                    DateAnswer da = new DateAnswer();
                    da.setAnswer(LocalDate.parse(request.getParameter(q.getCode())));
                    answer = da;
                } else if (q instanceof NumberQuestion) {
                    NumberAnswer na = new NumberAnswer();
                    na.setAnswer(Float.valueOf(request.getParameter(q.getCode())));
                    answer = na;
                } else if (q instanceof ShortTextQuestion) {
                    ShortTextAnswer sta = new ShortTextAnswer();
                    sta.setAnswer(request.getParameter(q.getCode()));
                    answer = sta;
                } else if (q instanceof TextQuestion){
                    TextAnswer ta = new TextAnswer();
                    ta.setAnswer(request.getParameter(q.getCode()));
                    answer = ta;
                } else {
                    continue;
                }
                answer.setQuestion(q);
                surveyResponse.addAnswer(answer);
            }
        }
        return surveyResponse;
    }
}
