/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import it.univaq.f4i.iw.pollweb.business.model.User;
import java.util.List;

/**
 *
 * @author vince
 */
public interface UserDAO {
    
    List<User> findAll();
    User findById(long id);
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    void saveOrUpdate(User user);
    void delete(User user);
}
