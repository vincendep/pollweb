/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller.page;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.pollweb.business.controller.BaseController;
import it.univaq.f4i.iw.pollweb.business.model.*;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vincenzo
 */
public class CompileSurveyPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            long surveyId = Long.valueOf(request.getParameter("id"));
            Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
            request.setAttribute("survey", survey);
            request.setAttribute("page_title", "Compile survey page");
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("compile-survey.ftlh", request, response);
        } catch (TemplateManagerException | NumberFormatException e) {
            throw new ServletException(e);
        } 
    }
}

