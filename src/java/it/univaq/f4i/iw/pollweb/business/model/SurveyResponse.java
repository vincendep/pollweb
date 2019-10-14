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

/**
 *
 * @author vince
 */
public class SurveyResponse {
    private Long id;
    private Survey survey;
    private LocalDate submissionDate;
    private Set<Answer> answers = new HashSet<>();
    
    public SurveyResponse() {
        this.submissionDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer a) {
        this.getAnswers().add(a);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.submissionDate);
        hash = 19 * hash + Objects.hashCode(this.answers);
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
        final SurveyResponse other = (SurveyResponse) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.submissionDate, other.submissionDate)) {
            return false;
        }
        if (!Objects.equals(this.answers, other.answers)) {
            return false;
        }
        return true;
    }
 
    public boolean isValid() {
        List<Question> answeredQuestions = new ArrayList<>();
        for (Answer answer: this.getAnswers()) {
            if (! answer.isValid()) {
                return false;
            }
            answeredQuestions.add(answer.getQuestion());
        }
        for (Question question: this.getSurvey().getQuestions()) {
            if (question.isMandatory() && (! answeredQuestions.contains(question))) {
                return false;
            }
        }
        return true;
    }
}
