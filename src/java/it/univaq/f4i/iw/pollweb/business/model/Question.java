/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author vince
 */
@XmlRootElement(name="question")
@XmlType (propOrder={"code", "mandatory", "text", "note", "answers"})
public abstract class Question {
    
    private Long id;
    private String code;
    private String text;
    private String note;
    private boolean mandatory;
    private Set<Answer> answers = new HashSet<>();

    public Question() {
        this.mandatory = true;
    }
    
    public Long getId() {
        return id;
    }

    @XmlTransient
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    @XmlAttribute
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getText() {
        return text;
    }

    @XmlElement(name = "text")
    public void setText(String text) {
        this.text = text;
    }

    public String getNote() {
        return note;
    }

    @XmlElement(name="note")
    public void setNote(String note) {
        this.note = note;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    @XmlAttribute
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    @XmlElement(name="answer", type = Answer.class)
    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
    
    public void addAnswer(Answer a) {
        this.getAnswers().add(a);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.code);
        hash = 97 * hash + Objects.hashCode(this.text);
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
        final Question other = (Question) obj;
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
