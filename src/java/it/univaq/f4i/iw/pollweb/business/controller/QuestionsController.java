/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Question;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
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
        // ((DataLayer) request.getAttribute("datalayer")).getQuestionDAO().delete(question);
        response.sendRedirect("/pollweb/account");
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("delete") != null) {
                action_delete(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
}
