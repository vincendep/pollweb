/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import java.time.LocalDate;

/**
 *
 * @author vince
 */
public class DateAnswer extends Answer {
    
    private LocalDate answer;

    public DateAnswer() {}
    
    public DateAnswer(LocalDate d, DateQuestion q) {
        this.answer = d;
        this.setQuestion(q);
    }
    
    public LocalDate getAnswer() {
        return answer;
    }

    public void setAnswer(LocalDate answer) {
        this.answer = answer;
    }

    @Override
    public boolean isValid(){ 
        if(this.getQuestion()!= null && (this.getQuestion() instanceof DateQuestion)){
            DateQuestion question =(DateQuestion) this.getQuestion();
        
            if(answer.isAfter(question.getMaxDate())){
                return false;       
            }
            if(answer.isBefore(question.getMinDate())){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
