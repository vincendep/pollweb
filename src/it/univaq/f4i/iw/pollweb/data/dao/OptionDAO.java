/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.data.dao;

import it.univaq.f4i.iw.pollweb.business.model.Option;

/**
 *
 * @author vincenzo
 */
public interface OptionDAO {
    
    Option findByid(Long id);
    void saveOrUpdate(Option option);
    void delete(Option option);
}
