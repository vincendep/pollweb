/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Question;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.utility.FormUtility;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
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
        survey.addQuestion(FormUtility.createQuestionModel(request));
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
    
    private void action_change_mandatory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        int questionIndex = SecurityLayer.checkNumeric(request.getParameter("question-index"));
        Question question = survey.getQuestions().get(questionIndex);
        question.setMandatory(! question.isMandatory());
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
            } else if (request.getParameter("change_mandatory") != null) {
                action_change_mandatory(request, response);
            } 
            else{
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }
}
