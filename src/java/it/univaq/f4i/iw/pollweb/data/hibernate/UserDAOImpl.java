/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.hibernate;

import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.data.dao.UserDAO;
import it.univaq.f4i.iw.pollweb.data.dao.DAO;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author vince
 */
class UserDAOImpl extends DAO implements UserDAO {
    
    public UserDAOImpl(Session s) {
        super(s);
    }

    @Override
    public User findById(long id) {
        return session.get(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        String hql = "FROM User WHERE email = :email";
        Query<User> query = session.createQuery(hql);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        String hql = "FROM User WHERE email = :email AND password = :password";
        TypedQuery<User> query = session.createQuery(hql);
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public void saveOrUpdate(User user) {
        session.saveOrUpdate(user);
    }

    @Override
    public void delete(User user) {
        session.delete(user);
    } 
}
