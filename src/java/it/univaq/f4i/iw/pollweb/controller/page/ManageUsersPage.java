/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.controller.page;

import it.univaq.f4i.iw.framework.result.TemplateManagerException;
import it.univaq.f4i.iw.framework.result.TemplateResult;
import it.univaq.f4i.iw.pollweb.business.controller.BaseController;
import it.univaq.f4i.iw.pollweb.business.model.Role;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vince
 */
public class ManageUsersPage extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            User user = (User) request.getAttribute("logged_user");
            if (user.getRole() != Role.ADMINISTRATOR) {
                throw new ServletException("L'utente corrente non è un amministratore");
            }
            List<User> users = ((DataLayer) request.getAttribute("datalayer")).getUserDAO().findAll();
            TemplateResult res = new TemplateResult((getServletContext()));
            request.setAttribute("page_title", "Manage users page");
            request.setAttribute("users", users);
            res.activate("manage-users.ftlh", request, response);
        } catch (TemplateManagerException e) {
            throw new ServletException(e);
        }
    }
    
}
