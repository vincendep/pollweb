/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.f4i.iw.pollweb.business.utility;

import it.univaq.f4i.iw.framework.security.SecurityLayer;
import it.univaq.f4i.iw.pollweb.business.model.Answer;
import it.univaq.f4i.iw.pollweb.business.model.ChoiceAnswer;
import it.univaq.f4i.iw.pollweb.business.model.ChoiceQuestion;
import it.univaq.f4i.iw.pollweb.business.model.DateAnswer;
import it.univaq.f4i.iw.pollweb.business.model.DateQuestion;
import it.univaq.f4i.iw.pollweb.business.model.NumberAnswer;
import it.univaq.f4i.iw.pollweb.business.model.NumberQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Option;
import it.univaq.f4i.iw.pollweb.business.model.Question;
import it.univaq.f4i.iw.pollweb.business.model.ShortTextAnswer;
import it.univaq.f4i.iw.pollweb.business.model.ShortTextQuestion;
import it.univaq.f4i.iw.pollweb.business.model.Survey;
import it.univaq.f4i.iw.pollweb.business.model.SurveyResponse;
import it.univaq.f4i.iw.pollweb.business.model.TextAnswer;
import it.univaq.f4i.iw.pollweb.business.model.TextQuestion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author vince
 */
public class FormUtility {
    
    public static Question createQuestionModel(HttpServletRequest request) {
        Question question;
        String text = request.getParameter("text");
        String code = request.getParameter("code");
        String note = request.getParameter("note");
        boolean mandatory = request.getParameter("mandatory") != null;
        switch(request.getParameter("type")) {
            case "short text":
                ShortTextQuestion stq = new ShortTextQuestion();
                if (request.getParameter("min-num-char") != null && request.getParameter("min-num-char") != "") {
                    stq.setMinLength(SecurityLayer.checkNumeric(request.getParameter("min-num-char")));
                }
                if (request.getParameter("max-num-char") != null && request.getParameter("max-num-char") != "") {
                    stq.setMaxLength(SecurityLayer.checkNumeric(request.getParameter("max-num-char")));
                }
                if (request.getParameter("pattern") != null && !"".equals(request.getParameter("pattern"))) {
                    stq.setPattern(request.getParameter("pattern"));
                }
                question = stq;
                break;
            case "long text":
                TextQuestion tq = new TextQuestion();
                if (request.getParameter("min-num-char") != null && request.getParameter("min-num-char") != "") {
                    tq.setMinLength(SecurityLayer.checkNumeric(request.getParameter("min-num-char")));
                }
                if (request.getParameter("max-num-char") != null && request.getParameter("max-num-char") != "") {
                    tq.setMaxLength(SecurityLayer.checkNumeric(request.getParameter("max-num-char")));
                }
                question = tq;
                break;
            case "choice":
                ChoiceQuestion cq = new ChoiceQuestion();
                if (request.getParameter("min-num-choices") != null && request.getParameter("min-num-choices") != "") {
                    cq.setMinNumberOfChoices(SecurityLayer.checkNumeric(request.getParameter("min-num-choices")));
                }
                if (request.getParameter("max-num-choices") != null && request.getParameter("max-num-choices") != "") {
                    cq.setMaxNumberOfChoices(SecurityLayer.checkNumeric(request.getParameter("max-num-choices")));
                }
                cq.setOptions(createOptionModel(request));
                question = cq;
                break;
            case "date":
                DateQuestion dq = new DateQuestion();
                if (request.getParameter("min-date") != null && request.getParameter("min-date") != "") {
                    dq.setMinDate(LocalDate.parse(request.getParameter("min-date")));
                }
                if (request.getParameter("max-date") != null && request.getParameter("max-date") != "") {
                    dq.setMaxDate(LocalDate.parse(request.getParameter("max-date")));
                }
                question = dq;
                break;
            case "number":
                NumberQuestion nq = new NumberQuestion();
                if (request.getParameter("min-num") != null && request.getParameter("min-num") != "") {
                    nq.setMinValue(SecurityLayer.checkNumeric(request.getParameter("min-num")));
                }
                if (request.getParameter("max-num") != null && request.getParameter("max-num") != "") {
                    nq.setMaxValue(SecurityLayer.checkNumeric(request.getParameter("max-num")));
                }
                question = nq;
                break;
            default:
                return null;
        }
        question.setText(text);
        question.setCode(code);
        question.setMandatory(mandatory);
        question.setNote(note);
        return question;
    }
    
    public static List<Option> createOptionModel(HttpServletRequest request) {
        List<Option> options = new ArrayList<>();
        String optionText = request.getParameter("choices");
        for (String opt: optionText.split(";")) {
            options.add(new Option(opt));
        }
        return options;
    }
    
    public static SurveyResponse createSurveyResponseModel(HttpServletRequest request, Survey survey) {
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setSurvey(survey);
        
        for (Question q: survey.getQuestions()) {
            if (request.getParameter(q.getCode()) != null) {
                Answer answer = null;
                if (q instanceof ChoiceQuestion) {
                    if (request.getParameterValues(q.getCode()).length > 0) {
                        ChoiceAnswer ca = new ChoiceAnswer();
                        for (String index: request.getParameterValues(q.getCode())) {
                            ca.addOption(((ChoiceQuestion) q).getOption(Integer.valueOf(index)));
                        }
                        answer = ca;
                    }                    
                } else if (q instanceof DateQuestion) {
                    if (request.getParameter(q.getCode()) != null && request.getParameter(q.getCode()) != "") {
                        DateAnswer da = new DateAnswer();
                        da.setAnswer(LocalDate.parse(request.getParameter(q.getCode())));
                        answer = da;
                    }
                } else if (q instanceof NumberQuestion) {
                    if (request.getParameter(q.getCode()) != null && request.getParameter(q.getCode()) != "") {
                        NumberAnswer na = new NumberAnswer();
                        na.setAnswer(Float.valueOf(request.getParameter(q.getCode())));
                        answer = na;
                    }   
                } else if (q instanceof ShortTextQuestion) {
                    if (request.getParameter(q.getCode()) != null && request.getParameter(q.getCode()) != "") {
                        ShortTextAnswer sta = new ShortTextAnswer();
                        sta.setAnswer(request.getParameter(q.getCode()));
                        answer = sta;
                    }
                } else if (q instanceof TextQuestion){
                    if (request.getParameter(q.getCode()) != null && request.getParameter(q.getCode()) != "") {
                        TextAnswer ta = new TextAnswer();
                        ta.setAnswer(request.getParameter(q.getCode()));
                        answer = ta;
                    }
                } 
                
                if (answer != null) {
                    answer.setQuestion(q);
                    surveyResponse.addAnswer(answer);
                }  
            }
        }
        return surveyResponse;
    }
}
