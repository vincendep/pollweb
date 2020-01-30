<div id="compile-survey">
    <div id="survey">
        <div id="opening" class="page opening" style="flex-direction: column;">
            <h2>${survey.title}</h2>
            <p>${survey.openingText}</p>
        </div>
        <form id="survey-form" action="/pollweb/compile-survey-controller?survey=${survey.id}" method="get">
            <input type="hidden" name="survey" value="#{survey.id}">
            <#list survey.questions as question>
                <section class="page question-card">
                    <h4>#{question?counter}. ${question.text?no_esc}</h4>
                    <#if question.note??><p>${question.note}</p></#if>
                    <#if question.class.simpleName == "ShortTextQuestion">
                        <input <#if question.mandatory>required="required"</#if> minlength="#{question.minLength}" maxlength="#{question.maxLength}" pattern="${question.pattern}" type="text" name="${question.code}"/>
                    <#elseif question.class.simpleName == "TextQuestion">
                        <textarea <#if question.mandatory>required="required"</#if> minlength="#{question.minLength}" maxlength="#{question.maxLength}" rows="4" name="${question.code}"></textarea>
                    <#elseif question.class.simpleName == "NumberQuestion">
                        <input <#if question.mandatory>required="required"</#if> min="${question.minValue}" max="${question.maxValue}" type="number" name="${question.code}"/>
                    <#elseif question.class.simpleName == "DateQuestion">
                        <input <#if question.mandatory>required="required"</#if> type="date" min="${question.minDate}" max="${question.maxDate}" name="${question.code}"/>
                    <#elseif question.class.simpleName == "ChoiceQuestion">
                        <fieldset style="border: none; position: relative;" <#if question.mandatory>data-required="required"</#if> data-min="${question.minNumberOfChoices}" data-max="${question.maxNumberOfChoices}" class="options">
                            <#list question.options as option>
                                <label for=${question.code}-${option?counter}>${option.text}</label>
                                <input id="${question.code}-${option?counter}" <#if question.maxNumberOfChoices == 1>type="radio"<#else>type="checkbox"</#if> name="${question.code}" value="${option?index}"/>
                            </#list>
                            <div class="error-message"></div>
                        </fieldset> 
                    </#if>
                </section>
            </#list>
        </form>
        <div id="closing" class="page closing" style="flex-direction: column;">
            <p>${survey.closingText}</p>
            <input form="survey-form" class="btn" type="submit" value="Conferma">
        </div>
    </div>
    <i id="larrow" class="arrow fas fa-arrow-left fa-2x"></i>
    <i id="rarrow" class="fas fa-arrow-right fa-2x"></i>
    
    <script src="/pollweb/js/compile-survey.js"></script>      
</div>