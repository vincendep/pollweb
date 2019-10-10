/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author vince
 */
public class ReservedSurvey extends Survey {
    
    private Set<Participant> participants = new TreeSet<>();

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }
}
