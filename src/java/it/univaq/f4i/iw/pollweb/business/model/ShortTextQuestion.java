/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

/**
 *
 * @author vincenzo
 */
public class ShortTextQuestion extends TextQuestion {
        
    private String pattern;

    public ShortTextQuestion() {
        this.pattern = ".*";
    }
    
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
