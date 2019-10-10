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
public class NumberAnswer extends Answer {
    
    private float answer;

    public float getAnswer() {
        return answer;
    }

    public void setAnswer(float answer) {
        this.answer = answer;
    }
    
    @Override
    public boolean isValid(){ 
        if(this.getQuestion()!= null && (this.getQuestion() instanceof NumberQuestion)){
            NumberQuestion question =(NumberQuestion) this.getQuestion();
            if((answer > question.getMaxValue())){
                return false;       
            }
            if((answer < question.getMinValue())){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
