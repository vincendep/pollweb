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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vince
 */
public class ManageParticipantsPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
            Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
            if (! (survey instanceof ReservedSurvey) || (! survey.getManager().equals(request.getAttribute("logged_user")))) {
                throw new ServletException("Parametro survey errato");
            }
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("survey", survey);
            request.setAttribute("page_title", "Manage participants page");
            res.activate("manage-participants.ftlh", request, response);
        } catch (NumberFormatException e) {
            throw new ServletException(e);
        } catch (TemplateManagerException ex) {
            Logger.getLogger(ManageParticipantsPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
