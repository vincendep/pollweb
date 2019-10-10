/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import it.univaq.f4i.iw.pollweb.business.model.User;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import java.util.List;

/**
 *
 * @author vince
 */
public interface SurveyDAO {
    
    Survey findById(long id);
    List<Survey> findByManager(User manager);
    List<Survey> findByActive(boolean active);
    List<Survey> findAllReserved();
    List<Survey> findAllNotReserved();
    List<Survey> findAll();
    void saveOrUpdate(Survey survey);
    void delete(Survey survey);
}
