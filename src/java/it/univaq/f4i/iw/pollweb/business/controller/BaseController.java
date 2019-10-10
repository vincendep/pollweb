/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.controller;

import it.univaq.f4i.iw.framework.result.FailureResult;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import it.univaq.f4i.iw.pollweb.data.hibernate.HibernateDataLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author vince
 */
public abstract class BaseController extends HttpServlet {
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processBaseRequest(request, response);
    }
    
    protected void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
    
    private void processBaseRequest(HttpServletRequest request, HttpServletResponse response) {
        DataLayer datalayer = new HibernateDataLayer();   
        request.setAttribute("datalayer", datalayer);
        Long userId = (Long) request.getSession().getAttribute("logged_user");
        if (userId != null) {
            request.setAttribute("logged_user", datalayer.getUserDAO().findById(userId));
        }
        try {
            processRequest(request, response);
        } catch (ServletException ex) {
            (new FailureResult(getServletContext())).activate(
                    (ex.getMessage() != null || ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage(), request, response);
        } finally {  
            datalayer.destroy(); 
        }
    }
    
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException;
}
