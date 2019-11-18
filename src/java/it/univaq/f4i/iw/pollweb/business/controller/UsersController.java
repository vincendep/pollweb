/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Role;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vince
 */
public class UsersController extends BaseController {

    private void action_add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        if ("administrator".equals(request.getParameter("role"))) {
            user.setRole(Role.ADMINISTRATOR);
        } else {
            user.setRole(Role.RESPONSIBLE);
        }
        ((DataLayer) request.getAttribute("datalayer")).getUserDAO().saveOrUpdate(user);
        response.sendRedirect("/pollweb/account/manage-users");
    }
    
    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = SecurityLayer.checkNumeric(request.getParameter("n"));
        User user = ((DataLayer) request.getAttribute("datalayer")).getUserDAO().findById(userId);
        ((DataLayer) request.getAttribute("datalayer")).getUserDAO().delete(user);
        response.sendRedirect("/pollweb/account/manage-users");
    }
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        User user = (User) request.getAttribute("logged_user");
        if (user.getRole() != Role.ADMINISTRATOR) {
            throw new ServletException("Non hai i permessi per eseguire questa operazione");
        }
        try {
            if (request.getParameter("add") != null) {
                action_add(request, response);
            } else if (request.getParameter("delete") != null) {
                action_delete(request, response);
            }
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }
    
}
