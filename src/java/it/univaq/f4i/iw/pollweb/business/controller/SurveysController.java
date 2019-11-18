package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.*;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import it.univaq.f4i.iw.pollweb.data.dao.SurveyDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SurveysController extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("create") != null) {
                action_create(request, response);
            } else if (request.getParameter("open") != null) {
                action_open(request, response);
            } else if (request.getParameter("close") != null) {
                action_close(request, response);
            } else if (request.getParameter("delete") != null) {
                action_delete(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (IOException e) {
            throw  new ServletException(e);
        }
    }
    
    private void action_create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Survey survey = "reserved".equals(request.getParameter("type")) ? new ReservedSurvey() : new Survey();
        survey.setTitle(request.getParameter("title"));
        survey.setOpeningText(request.getParameter("openingText"));
        survey.setClosingText(request.getParameter("closingText"));
        survey.setManager((User) request.getAttribute("logged_user"));
        if (survey.getTitle() != null && ! (survey.getTitle().equals(""))) {
            SurveyDAO dao = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO();
            dao.saveOrUpdate(survey);
            response.sendRedirect("account");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void action_open(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        if (survey != null) {
            if (! survey.getManager().equals(request.getAttribute("logged_user"))) {
                throw new ServletException("Non sei il responsabile di questo sondaggio");
            }
            survey.setActive(true);
            response.sendRedirect("account/survey-details?survey=" + surveyId);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void action_close(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        if (survey != null) {
            if (! survey.getManager().equals(request.getAttribute("logged_user"))) {
                throw new ServletException("Non sei il responsabile di questo sondaggio");
            }
            survey.setActive(false);
            response.sendRedirect("account/survey-details?n=" + surveyId);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int surveyId = SecurityLayer.checkNumeric(request.getParameter("survey"));
        Survey survey = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findById(surveyId);
        
        if (survey != null) {
            if (! survey.getManager().equals(request.getAttribute("logged_user"))) {
                throw new ServletException("Non sei il responsabile di questo sondaggio");
            }
            ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().delete(survey);
            response.sendRedirect("account");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
