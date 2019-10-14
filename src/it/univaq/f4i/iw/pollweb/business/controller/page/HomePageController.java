/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller.page;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.pollweb.business.controller.BaseController;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author vince
 */
public class HomePageController extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            List<Survey> surveys = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findAll();
            request.setAttribute("surveys", surveys);
            request.setAttribute("page_title", "Homepage");
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("home.ftlh", request, response);
        } catch (TemplateManagerException ex) {
            throw new ServletException(ex);
        }
    }
}
