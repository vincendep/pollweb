package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.*;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import it.univaq.f4i.iw.pollweb.data.dao.SurveyDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SurveysController extends BaseController {

    private void action_submit(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("n"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        SurveyResponse surveyResponse = null;

        if (survey instanceof ReservedSurvey) {
            ReservedSurvey reservedSurvey = (ReservedSurvey) survey;
            Long participantId = (Long) request.getSession().getAttribute("auth_participant");
            if (participantId == null) {
                request.setAttribute("message", "Authentication needed to submit this survey");
                action_error(request, response);
            } else {
                Participant participant = ((DataLayer) request.getAttribute("datalayer")).getParticipantDAO().findById(participantId);
                if (! participant.getReservedSurvey().equals(reservedSurvey)) {
                    request.setAttribute("message", "Not authorized to submit this survey");
                    action_error(request, response);
                } else if (participant.hasAlreadySubmitted()){
                    request.setAttribute("message", "You can't submit the survey again");
                    action_error(request, response);
                } else {
                    surveyResponse = createSurveyResponseFromRequest(request, survey);
                    if (surveyResponse.isValid()) {
                        ((DataLayer) request.getAttribute("datalayer")).getSurveyResponseDAO().saveOrUpdate(surveyResponse);
                        participant.setSubmitted(true);
                        TemplateResult res = new TemplateResult(getServletContext());
                        request.setAttribute("page_title", "Confirm compilation page");
                        request.setAttribute("survey", survey);
                        res.activate("compilation-confirm.ftlh", request, response);
                    } else {
                        request.setAttribute("message", "Compilation not valid, please try again");
                        action_error(request, response);
                    }
                }
            }
            // public survey
        } else {
            surveyResponse = createSurveyResponseFromRequest(request, survey);
            if (surveyResponse.isValid()) {
                ((DataLayer) request.getAttribute("datalayer")).getSurveyResponseDAO().saveOrUpdate(surveyResponse);
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("page_title", "Confirm compilation page");
                request.setAttribute("survey", survey);
                res.activate("compilation-confirm.ftlh", request, response);
            } else {
                request.setAttribute("message", "Compilation not valid, please try again");
                action_error(request, response);
            }
        }
    }

    private void action_create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Survey survey = "Riservato".equals(request.getParameter("type")) ? new ReservedSurvey() : new Survey();
        survey.setTitle(request.getParameter("title"));
        survey.setOpeningText(request.getParameter("openingText"));
        survey.setClosingText(request.getParameter("closingText"));
        survey.setManager((User) request.getAttribute("logged_user"));
        if (survey.getTitle() != null && ! (survey.getTitle().equals(""))) {
            SurveyDAO dao = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO();
            dao.saveOrUpdate(survey);
            response.sendRedirect("account");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void action_open(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("n"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        if (survey != null) {
            survey.setActive(true);
            response.sendRedirect("account");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void action_close(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("n"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        if (survey != null) {
            survey.setActive(false);
            response.sendRedirect("account");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void action_publish(HttpServletRequest request, HttpServletResponse response) {
        
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("submit") != null) {
                action_submit(request, response);
            } else if (request.getParameter("publish") != null) {
                action_publish(request, response);
            } else if (request.getParameter("create") != null) {
                action_create(request, response);
            } else if (request.getParameter("open") != null) {
                action_open(request, response);
            } else if (request.getParameter("close") != null) {
                action_close(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (TemplateManagerException e) {
            throw  new ServletException(e);
        } catch (IOException e) {
            Logger.getLogger(SurveysController.class.getName()).log(Level.SEVERE, null, e);
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
