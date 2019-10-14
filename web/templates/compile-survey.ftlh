<h2>${survey.title}</h2>
<p>${survey.openingText}</p>
<form action="/pollweb/submit-survey" method="post">
    <input type="hidden" name="survey" value="#{survey.id}">
    <#list survey.questions as question>
        <h4>#{question?counter}. ${question.text}</h4>
        <#if question.getQuestionType() == "short text">
            <input type="text" name="${question.code}"/>
        <#elseif question.getQuestionType() == "long text">
            <textarea name="${question.code}"></textarea>
        <#elseif question.getQuestionType() == "number">
            <input type="number" name="${question.code}"/>
        <#elseif question.getQuestionType() == "date">
            <input type="date" name="${question.code}"/>
        <#elseif question.getQuestionType() == "single choice">
            <#list question.options as option>
                <input type="radio" name="${question.code}" value="${option?index}">${option.text}</br>
            </#list>
        <#elseif question.getQuestionType() == "multiple choice">
            <#list question.options as option>
                <input type="checkbox" name="${question.code}" value="${option?index}">${option.text}</br>
            </#list>
        </#if>
    </#list>
    <p>${survey.closingText}</p>
    <input type="submit" name="submit" value="Conferma" class="medium" />
</form>


