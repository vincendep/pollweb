/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vince
 */
public class ChoiceQuestion extends Question {
     
    private int minNumberOfChoices;
    private int maxNumberOfChoices;
    private List<Option> options = new ArrayList<>();

    public ChoiceQuestion() {
        this.minNumberOfChoices = 0;
        this.maxNumberOfChoices = 50;
    }

    public int getMinNumberOfChoices() {
        return minNumberOfChoices;
    }

    public void setMinNumberOfChoices(int minNumberOfChoices) {
        if (minNumberOfChoices > 0) {
            super.setMandatory(true);
        } else {
            super.setMandatory(false);
        }
        this.minNumberOfChoices = minNumberOfChoices;
    }
    
    @Override
    public void setMandatory(boolean mandatory) {
        if (this.minNumberOfChoices == 0) {
            super.setMandatory(false);
        } else {
            super.setMandatory(true);
        }
    }
    
    public int getMaxNumberOfChoices() {
        return maxNumberOfChoices;
    }

    public void setMaxNumberOfChoices(int maxNumberOfChoices) {
        this.maxNumberOfChoices = maxNumberOfChoices;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
    
    public Option getOption(int index) {
        return this.getOptions().get(index );
    }
    
    public void addOption(Option  option) {
        this.options.add(option);
    }
}
