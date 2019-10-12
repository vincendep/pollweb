<h2>${survey.title}</h2>
<p>${survey.openingText}</p>
<form action="/pollweb/compile-survey?id=#{survey.id}" method="post">
    <#list survey.questions as question>
        <h4>#{question?counter}. ${question.text}</h4>
        <#if question.getQuestionType() == "short text">
            <#include "questions/short-text-question.ftlh">
        <#elseif question.getQuestionType() == "long text">
            <#include "questions/long-text-question.ftlh">
        <#elseif question.getQuestionType() == "number">
            <#include "questions/number-question.ftlh">
        <#elseif question.getQuestionType() == "date">
            <#include "questions/date-question.ftlh">
        <#elseif question.getQuestionType() == "single choice">
            <#include "questions/single-choice-question.ftlh">
        <#elseif question.getQuestionType() == "multiple choice">
            <#include "questions/multiple-choice-question.ftlh">
        </#if>
    </#list>
    </br>
    <input type="submit" name="submit" value="Conferma" class="medium" />
</form>
<p>${survey.closingText}</p>
