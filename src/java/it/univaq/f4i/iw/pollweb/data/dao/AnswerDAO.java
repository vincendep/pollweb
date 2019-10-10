/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import it.univaq.f4i.iw.pollweb.business.model.Answer;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import java.util.List;

/**
 *
 * @author vince
 */
public interface AnswerDAO {
    
    Answer findById(Long id);
    void saveOrUpdate(Answer answer);
    void delete(Answer answer);
}
