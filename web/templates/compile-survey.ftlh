<div class="survey-sheet">
    <h2>${survey.title}</h2>
    <p>${survey.openingText}</p>
    <form action="/pollweb/surveys?n=${survey.id}" method="post">
        <input type="hidden" name="survey" value="#{survey.id}">
        <#list survey.questions as question>
            <div class="question">
                <h4>#{question?counter}. ${question.text}</h4>
                <#if question.note??><p>${question.note}</p></#if>
                <#if question.class.simpleName == "ShortTextQuestion">
                    <input type="text" name="${question.code}"/>
                <#elseif question.class.simpleName == "TextQuestion">
                    <textarea rows="4" name="${question.code}"></textarea>
                <#elseif question.class.simpleName == "NumberQuestion">
                    <input type="number" name="${question.code}"/>
                <#elseif question.class.simpleName == "DateQuestion">
                    <input type="date" name="${question.code}"/>
                <#elseif question.class.simpleName == "ChoiceQuestion">
                    <div class="options">
                        <#list question.options as option>
                            <label>
                                <input <#if question.maxNumberOfChoices == 1>type="radio"<#else>type="checkbox"</#if> name="${question.code}" value="${option?index}"/> ${option.text}
                            </label>
                        </#list>
                    </div>
                </#if>
            </div>
        </#list>
        <p>${survey.closingText}</p>
        <input class="btn" type="submit" name="submit" value="Conferma" class="medium" />
    </form>
</div>

