package it.univaq.f4i.iw.pollweb.business.controller.pages;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.pollweb.business.controller.BaseController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AboutPageController extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            request.setAttribute("page_title", "About page");
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("about.ftlh", request, response);
        } catch (TemplateManagerException e) {
            throw new ServletException(e);
        }
    }
}
