/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

/**
 *
 * @author vince
 */
public class TextQuestion extends Question {
       
    private int minLength;
    private int maxLength;

    public TextQuestion() {
        this.minLength = 0;
        this.maxLength = 1000000;
    }
    
    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
