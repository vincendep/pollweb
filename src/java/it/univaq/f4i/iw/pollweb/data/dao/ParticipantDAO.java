/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import it.univaq.f4i.iw.pollweb.business.model.Participant;

/**
 *
 * @author vince
 */
public interface ParticipantDAO {
    
    Participant findById(Long id);
    Participant findByEmailAndPasswordAndSurveyId(String email, String password, Long surveyId);
    void saveOrUpdate(Participant participant);
    void delete(Participant participant);
}
