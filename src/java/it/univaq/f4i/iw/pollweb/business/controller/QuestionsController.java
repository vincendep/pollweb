/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.ChoiceQuestion;
import it.univaq.f4i.iw.pollweb.business.model.DateQuestion;
import it.univaq.f4i.iw.pollweb.business.model.NumberQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Option;
import it.univaq.f4i.iw.pollweb.business.model.Question;
import it.univaq.f4i.iw.pollweb.business.model.ShortTextQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.TextQuestion;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vincenzo
 */
public class QuestionsController extends BaseController {

    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        int questionIndex = SecurityLayer.checkNumeric(request.getParameter("question-index"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        Question question = survey.removeQuestion(questionIndex);
        // need management of answers fk to question
        ((DataLayer) request.getAttribute("datalayer")).getQuestionDAO().delete(question);
        response.sendRedirect("account/survey-details?survey=" + surveyId);
    }

    private void action_create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        survey.addQuestion(createQuestionFromRequest(request));
        ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().saveOrUpdate(survey);
        response.sendRedirect("account/survey-details?survey=" + surveyId);
    }
    
    private void action_up(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        int questionIndex = SecurityLayer.checkNumeric(request.getParameter("question-index"));
        if (questionIndex > 0) {
            survey.swapQuestions(questionIndex, questionIndex - 1);
        }
        response.sendRedirect("account/survey-details?survey=" + surveyId);
    }
    
    private void action_down(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        int questionIndex = SecurityLayer.checkNumeric(request.getParameter("question-index"));
        if (questionIndex < survey.getQuestions().size() - 1) {
            survey.swapQuestions(questionIndex, questionIndex + 1);
        }
        response.sendRedirect("account/survey-details?survey=" + surveyId);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("delete") != null) {
                action_delete(request, response);
            } else if (request.getParameter("create") != null) {
                action_create(request, response);
            } else if (request.getParameter("up") != null) {
                action_up(request, response);
            } else if (request.getParameter("down") != null) {
                action_down(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }
    
    private Question createQuestionFromRequest(HttpServletRequest request) {
        Question question = null;
        String text = request.getParameter("text");
        String code = request.getParameter("code");
        String note = request.getParameter("note");
        boolean mandatory = request.getParameter("mandatory") != null;
        switch(request.getParameter("type")) {
            case "short text":
                int minChar = SecurityLayer.checkNumeric(request.getParameter("min-num-char"));
                int maxChar = SecurityLayer.checkNumeric(request.getParameter("max-num-char"));
                String pattern = request.getParameter("pattern");
                ShortTextQuestion stq = new ShortTextQuestion();
                stq.setMinLength(minChar);
                stq.setMaxLength(maxChar);
                question = stq;
                break;
            case "long text":
                int minChar_ = SecurityLayer.checkNumeric(request.getParameter("min-num-char"));
                int maxChar_ = SecurityLayer.checkNumeric(request.getParameter("max-num-char"));
                TextQuestion tq = new TextQuestion();
                tq.setMinLength(minChar_);
                tq.setMaxLength(maxChar_);
                question = tq;
                break;
            case "choice":
                int minChoices = SecurityLayer.checkNumeric(request.getParameter("min-num-choices"));
                int maxChoices = SecurityLayer.checkNumeric(request.getParameter("max-num-choices"));
                ChoiceQuestion cq = new ChoiceQuestion();
                cq.setMinNumberOfChoices(minChoices);
                cq.setMaxNumberOfChoices(maxChoices);
                cq.setOptions(createOptionsFromRequest(request));
                question = cq;
                break;
            case "date":
                LocalDate minDate = LocalDate.parse(request.getParameter("min-date"));
                LocalDate maxDate = LocalDate.parse(request.getParameter("max-date"));
                DateQuestion dq = new DateQuestion();
                dq.setMinDate(minDate);
                dq.setMaxDate(maxDate);
                question = dq;
                break;
            case "number":
                int minValue = SecurityLayer.checkNumeric(request.getParameter("min-num"));
                int maxValue = SecurityLayer.checkNumeric(request.getParameter("max-num"));
                NumberQuestion nq = new NumberQuestion();
                nq.setMinValue(minValue);
                nq.setMaxValue(maxValue);
                question = nq;
                break;
            default:
                return null;
        }
        question.setText(text);
        question.setCode(code);
        question.setMandatory(mandatory);
        question.setNote(note);
        return question;
    }
    
    private List<Option> createOptionsFromRequest(HttpServletRequest request) {
        List<Option> options = new ArrayList<>();
        String optionText = request.getParameter("choices");
        for (String opt: optionText.split(";")) {
            options.add(new Option(opt));
        }
        return options;
    }
}
