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

public class SurveyResultPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (Utility.userManageSurvey(request)) {
                int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
                DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
                Survey survey = dataLayer.getSurveyDAO().findById(surveyId);
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("page_title","Survey result page");
                request.setAttribute("survey", survey);
                res.activate("survey-results.ftlh", request, response);
            } else {
                response.sendRedirect("/pollweb");
            }
        } catch (NumberFormatException | TemplateManagerException ex) {
            throw new ServletException(ex);
        } catch (IOException ex) {
            Logger.getLogger(SurveyResultPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
