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
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vincenzo
 */
public class CreateQuestionPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("survey") == null || request.getParameter("type") == null) {
                throw new ServletException("Parametro mancante");
            }
            String typeParam = request.getParameter("type");
            if ((!"short text".equals(typeParam)) && (!"long text".equals(typeParam)) && (!"number".equals(typeParam)) && (!"date".equals(typeParam)) && (!"choice".equals(typeParam))) {
                throw new ServletException("Parametro type invalido");
            }
            long surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
            Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
            if (survey == null || ! (survey.getManager().equals(request.getAttribute("logged_user")))) {
                throw new ServletException("Parametro survey invalido");
            }
            request.setAttribute("survey", survey.getId());
            request.setAttribute("page_title", "Create question page");
            request.setAttribute("type", request.getParameter("type"));
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("create-question.ftlh", request, response);
        } catch (TemplateManagerException |NumberFormatException e) {
            throw new ServletException(e);
        }
    }
    
}
