/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;

import java.util.List;

/**
 *
 * @author vince
 */
public interface SurveyResponseDAO {
    
    SurveyResponse findById(long id);
    List<SurveyResponse> findBySurvey(Survey survey);
    void saveOrUpdate(SurveyResponse sr);
    void delete(SurveyResponse sr);
}
