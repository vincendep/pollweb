package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.pollweb.business.model.*;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubmitSurveyController extends BaseController {

    private void action_submit(HttpServletRequest request, HttpServletResponse response, Survey survey) throws TemplateManagerException {
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

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int surveyId = Integer.valueOf(request.getParameter("survey"));
            Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
            if (survey != null) {
                action_submit(request, response, survey);
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "400 BAD REQUEST");
            action_error(request, response);
        } catch (TemplateManagerException e) {
            Logger.getLogger(SubmitSurveyController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
