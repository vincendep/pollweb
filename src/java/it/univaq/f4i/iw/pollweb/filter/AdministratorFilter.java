/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.filter;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Role;
import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.DataLayer;
import it.univaq.f4i.iw.pollweb.data.hibernate.HibernateDataLayer;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author vince
 */
public class AdministratorFilter implements Filter {

    private DataLayer dataLayer;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dataLayer = new HibernateDataLayer();
    }
    
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;
        HttpSession session = SecurityLayer.checkSession(request);
        if (session == null) {
            response.sendRedirect("/pollweb");
        } else {
            int userId = (Integer) session.getAttribute("userid");
            User user = dataLayer.getUserDAO().findById(userId);
            if (user.getRole() != Role.ADMINISTRATOR) {
                response.sendRedirect("/pollweb");
            }
        }
        fc.doFilter(sr, sr1);
    }

    @Override
    public void destroy() {
        this.dataLayer.destroy();
    } 
}
