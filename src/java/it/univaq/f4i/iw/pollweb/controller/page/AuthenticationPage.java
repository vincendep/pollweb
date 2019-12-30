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
import it.univaq.f4i.iw.pollweb.business.model.ReservedSurvey;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vince
 */
public class AuthenticationPage extends BaseController {

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
            if (request.getParameter("survey") == null) {
                throw new ServletException("Parametro survey mancante");
            } 
            long surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
            Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
            if (survey == null || ! (survey instanceof ReservedSurvey)) {
                throw new ServletException("Parametro survey invalido");
            } 
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Authentication page");
            request.setAttribute("survey", survey.getId());
            res.activate("authentication.ftlh", request, response);
        } catch (TemplateManagerException |NumberFormatException e) {
            throw new ServletException(e);
        }
    }
}
