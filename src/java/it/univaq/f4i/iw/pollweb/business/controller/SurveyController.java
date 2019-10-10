/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
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
public class SurveyController extends BaseController {

    private void action_default(HttpServletRequest request, HttpServletResponse response, Survey survey) throws TemplateManagerException, IOException {
        if (! survey.isActive()) {
            request.setAttribute("message", "The survey is closed and no longer fillable! :(");
            action_error(request, response);
        } else {
            request.setAttribute("survey", survey);
            request.setAttribute("page_title", survey.getTitle());
            TemplateResult res = new TemplateResult(getServletContext());    
            res.activate("survey.ftl.html", request, response);
        }   
    }
    
    private void action_submit(HttpServletRequest request, HttpServletResponse response) {
        
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("id") == null) {
                request.setAttribute("message", "Parametro mancante");
                action_error(request, response);
            } else {
                long surveyId = Long.valueOf(request.getParameter("id"));
                Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
                if (survey != null) {
                    if (request.getParameter("submit") != null) {
                        action_submit(request, response);
                    } else {
                        action_default(request, response, survey);
                    }     
                } else {
                    request.setAttribute("message", "Parametro incorretto");
                    action_error(request, response);
                }
            }
        } catch (TemplateManagerException | IOException | NumberFormatException e) {
            request.setAttribute("exception", e);
            action_error(request, response);
        } 
    }
}

