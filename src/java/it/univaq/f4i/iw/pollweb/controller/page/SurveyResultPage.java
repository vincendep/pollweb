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

public class SurveyResultPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
            Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
            if (survey == null || ! survey.getManager().equals(request.getAttribute("logged_user"))) {
                throw new ServletException("Parametro survey invalido");
            }
            request.setAttribute("page_title","Survey result page");
            request.setAttribute("survey", survey);
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("survey-results.ftlh", request, response);
            
        } catch (NumberFormatException | TemplateManagerException ex) {
            throw new ServletException(ex);
        }
    }
}
