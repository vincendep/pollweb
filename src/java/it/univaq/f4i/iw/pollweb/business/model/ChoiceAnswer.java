/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author vince
 */
public class ChoiceAnswer extends Answer {
    
    private Set<Option> options = new HashSet<>();
    
    public ChoiceAnswer() {
    }

    public Set<Option> getOptions() {
        return options;
    }

    @XmlElement(name = "choice")
    public void setOptions(Set<Option> options) {
        this.options = options;
    }
    
    public void addOption(Option o) {
        this.getOptions().add(o);
    }
    
    @Override
    public boolean isValid() {
        if (this.getQuestion() != null && (this.getQuestion() instanceof ChoiceQuestion)) {
            ChoiceQuestion cq = (ChoiceQuestion) this.getQuestion();
            if (cq.getMinNumberOfChoices() > this.getOptions().size()) {
                return false;
            }
            if (cq.getMaxNumberOfChoices() < this.getOptions().size()) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
