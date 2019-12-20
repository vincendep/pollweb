/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@XmlRootElement(name = "survey")
@XmlType (propOrder={"title", "openingText", "closingText", "questions"})
public class Survey {
    private Long id;
    private String title;
    private String openingText;
    private String closingText;
    private LocalDate pubblicationDate;
    private List<Question> questions = new ArrayList<>();
    private User manager;
    private boolean active;
    private Set<SurveyResponse> surveyResponses = new HashSet<>();

    public Survey() {
        this.pubblicationDate = LocalDate.now();
    }
    
    public Long getId() {
        return id;
    }

    @XmlTransient
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @XmlAttribute
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getOpeningText() {
        return openingText;
    }

    @XmlElement(name="opening-text")
    public void setOpeningText(String openingText) {
        this.openingText = openingText;
    }

    public String getClosingText() {
        return closingText;
    }

    @XmlElement(name="closing-text")
    public void setClosingText(String closingText) {
        this.closingText = closingText;
    }

    public LocalDate getPubblicationDate() {
        return pubblicationDate;
    }

    @XmlTransient
    public void setPubblicationDate(LocalDate pubblicationDate) {
        this.pubblicationDate = pubblicationDate;
    }
    
    public List<Question> getQuestions() {
        return questions;
    }

    @XmlElement(name="question")
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    
    public void addQuestion(Question q) {
        getQuestions().add(q);
    }
    
    public Question removeQuestion(int index) {
        return this.questions.remove(index);
    }

    public void swapQuestions(int index1, int index2) {
        Question q1 = this.questions.get(index1);
        Question q2 = this.questions.get(index2);
        this.questions.set(index1, q2);
        this.questions.set(index2, q1);
    }
    
    public User getManager() {
        return manager;
    }

    @XmlTransient
    public void setManager(User manager) {
        this.manager = manager;
    }

    public boolean isActive() {
        return active;
    }

    @XmlTransient
    public void setActive(boolean active) {
        this.active = active;
    } 

    public Set<SurveyResponse> getSurveyResponses() {
        return surveyResponses;
    }

    @XmlTransient
    public void setSurveyResponses(Set<SurveyResponse> responses) {
        this.surveyResponses = responses;
    }
    
    public void addSurveyResponse(SurveyResponse sr) {
        getSurveyResponses().add(sr);
        sr.setSurvey(this);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.title);
        hash = 47 * hash + Objects.hashCode(this.openingText);
        hash = 47 * hash + Objects.hashCode(this.closingText);
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
        final Survey other = (Survey) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.openingText, other.openingText)) {
            return false;
        }
        if (!Objects.equals(this.closingText, other.closingText)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
