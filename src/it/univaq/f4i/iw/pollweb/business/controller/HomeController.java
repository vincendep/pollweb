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
import it.univaq.f4i.iw.pollweb.data.hibernate.HibernateDataLayer;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author vince
 */
public class HomeController extends BaseController {
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {
        Long userId = (Long) request.getSession().getAttribute("logged_user");
        if (userId != null) {
            request.setAttribute("logged_user", ((DataLayer) request.getAttribute("datalayer")).getUserDAO().findById(userId));
        }
        List<Survey> surveys = ((HibernateDataLayer) request.getAttribute("datalayer")).getSurveyDAO().findAll();
        request.setAttribute("surveys", surveys);
        TemplateResult res = new TemplateResult(getServletContext());
        res.activate("homepage.ftlh", request, response);
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_default(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("message", ex.getMessage());
            action_error(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "HomeController";
    }
}
