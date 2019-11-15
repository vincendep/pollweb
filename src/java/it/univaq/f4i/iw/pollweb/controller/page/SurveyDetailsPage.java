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
import it.univaq.f4i.iw.pollweb.business.controller.Utility;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vincenzo
 */
public class SurveyDetailsPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (Utility.userManageSurvey(request)) {
                int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
                Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
                request.setAttribute("survey", survey);
                request.setAttribute("page_title", "Survey details page");
                TemplateResult res = new TemplateResult(getServletContext());
                res.activate("survey-details.ftlh", request, response);
            } else {
                response.sendRedirect("/pollweb");
            }
        } catch (TemplateManagerException ex) {
            throw new ServletException(ex);
        } catch (IOException ex) {
            Logger.getLogger(SurveyDetailsPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
