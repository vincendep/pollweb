/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author vince
 */
public class TextAnswer extends Answer {
    
    private String answer;

    public String getAnswer() {
        return answer;
    }

    @XmlElement(name="value")
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    @Override
    public boolean isValid(){ 
        if(this.getQuestion()!= null && (this.getQuestion() instanceof TextQuestion)){
            TextQuestion question =(TextQuestion) this.getQuestion();
        
            if(answer.length()>question.getMaxLength()){
                return false;       
            }
            if(answer.length() < question.getMinLength()){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
