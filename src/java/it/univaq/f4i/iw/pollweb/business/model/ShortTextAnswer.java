/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import java.util.regex.Pattern;

/**
 *
 * @author vince
 */
public class ShortTextAnswer extends TextAnswer {

    @Override
    public boolean isValid() {
        if(this.getQuestion()!= null && (this.getQuestion() instanceof ShortTextQuestion)){
            ShortTextQuestion question =(ShortTextQuestion) this.getQuestion();
        
            if(getAnswer().length() > question.getMaxLength()){
                return false;       
            }
            if(getAnswer().length() < question.getMinLength()){
                return false;
            }
            if (! Pattern.matches(question.getPattern(), this.getAnswer())) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
