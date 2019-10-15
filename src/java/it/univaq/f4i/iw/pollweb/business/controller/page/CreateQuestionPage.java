/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller.page;

import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.pollweb.business.controller.BaseController;
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
            request.setAttribute("survey", request.getParameter("survey"));
            request.setAttribute("page_title", "Create question page");
            request.setAttribute("type", request.getParameter("type"));
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("create-question.ftlh", request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    
}
