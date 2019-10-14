package it.univaq.f4i.iw.pollweb.business.controller.page;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.pollweb.business.controller.BaseController;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AccountPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            List<Survey> surveys = ((DataLayer) request.getAttribute("datalayer")).getSurveyDAO().findByManager((User) request.getAttribute("logged_user"));
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Account page");
            request.setAttribute("surveys", surveys);
            res.activate("account.ftlh", request, response);
        } catch (TemplateManagerException e) {
            throw new ServletException(e);
        }
    }
}
