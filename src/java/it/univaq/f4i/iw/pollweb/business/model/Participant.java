/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import java.util.Objects;

/**
 * @author vince
 */
public class Participant {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private ReservedSurvey reservedSurvey;
    private boolean submitted;

    public Participant() {
        this.submitted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSubmitted(boolean b) {
        this.submitted = b;
    }

    public boolean isSubmitted() {
        return this.submitted;
    }

    public ReservedSurvey getReservedSurvey() {
        return reservedSurvey;
    }

    public void setReservedSurvey(ReservedSurvey reservedSurvey) {
        this.reservedSurvey = reservedSurvey;
    }

    public boolean hasAlreadySubmitted() {
        return this.submitted;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Participant other = (Participant) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
    
}
