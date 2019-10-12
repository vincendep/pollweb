package it.univaq.f4i.iw.pollweb.business.controller.pages;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.controller.BaseController;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SurveyResultPageController extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            int surveyId = SecurityLayer.checkNumeric(request.getParameter("n"));
            DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
            Survey survey = dataLayer.getSurveyDAO().findById(surveyId);
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("title","Survey results page");
            request.setAttribute("survey", survey);
            res.activate("survey-results.ftlh", request, response);
        } catch (NumberFormatException | TemplateManagerException ex) {
            throw new ServletException(ex);
        }
    }
}
