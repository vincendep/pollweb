<div class="survey-sheet">
    <h2>${survey.title}</h2>
    <p>${survey.openingText}</p>
    <form action="/pollweb/surveys?n=${survey.id}" method="post">
        <input type="hidden" name="survey" value="#{survey.id}">
        <#list survey.questions as question>
            <div class="question">
                <h4>#{question?counter}. ${question.text}</h4>
                <#if question.note??><p>${question.note}</p></#if>
                <#if question.getQuestionType() == "short text">
                    <input type="text" name="${question.code}"/>
                <#elseif question.getQuestionType() == "long text">
                    <textarea rows="4" name="${question.code}"></textarea>
                <#elseif question.getQuestionType() == "number">
                    <input type="number" name="${question.code}"/>
                <#elseif question.getQuestionType() == "date">
                    <input type="date" name="${question.code}"/>
                <#elseif question.getQuestionType() == "single choice">
                    <div class="options">
                        <#list question.options as option>
                            <label><input type="radio" name="${question.code}" value="${option?index}"/> ${option.text}</label>
                        </#list>
                    </div>
                <#elseif question.getQuestionType() == "multiple choice">
                    <div class="options">
                        <#list question.options as option>
                            <label><input type="checkbox" name="${question.code}" value="${option?index}"/> ${option.text} </label>
                        </#list>
                    </div>
                </#if>
            </div>
        </#list>
        <p>${survey.closingText}</p>
        <input class="btn" type="submit" name="submit" value="Conferma" class="medium" />
    </form>
</div>

