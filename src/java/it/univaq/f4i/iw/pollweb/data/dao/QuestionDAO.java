/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import it.univaq.f4i.iw.pollweb.business.model.Question;

/**
 *
 * @author vince
 */
public interface QuestionDAO {
    
    Question findById(long id);
    void saveOrUpdate(Question question);
    void delete(Question question);
}
