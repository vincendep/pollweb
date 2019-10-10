/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pagliarini Alberto
 */
public class AuthenticationController extends BaseController {
    
    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email != null && password != null) {
            DataLayer dataLayer = (DataLayer) request.getAttribute("datalayer");
            User user = dataLayer.getUserDAO().findByEmailAndPassword(email, password);
            if (user != null) {
                request.getSession().setAttribute("logged_user", user.getId());
                response.sendRedirect("home");
            } else {
                request.setAttribute("message", "Username o password errati");
                action_error(request, response);
            }
        } else {
            request.setAttribute("message", "Missing params");
        } 
    }
    
    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().removeAttribute("logged_user");
        response.sendRedirect("home");
    }
    
    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("page_title", "Accedi");
            res.activate("login-form.ftl.html", request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("message", "Template manager exception: " + ex.getMessage());
            action_error(request, response);
        }
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else if (request.getParameter("logout") != null) {
                action_logout(request, response);
            } else {
                action_default(request, response);
            }
        } catch ( IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }
        
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "AuthenticationController";
    }// </editor-fold>
}
