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
public class DateQuestion extends Question {
        
    private LocalDate minDate;
    private LocalDate maxDate;
    
    public DateQuestion(){
        this.minDate = LocalDate.of(1000, 1, 1);
        this.maxDate = LocalDate.of(2999, 12, 31);
    }

    public LocalDate getMinDate() {
        return minDate;
    }

    public void setMinDate(LocalDate minDate) {
        this.minDate = minDate;
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
    }

    @Override
    public String getQuestionType() {
        return "date";
    }
}
