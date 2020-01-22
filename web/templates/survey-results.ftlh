<!-- evita hibernate proxy -->
<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>

<#list survey.surveyResponses as response>
  <div class="response-header">
    <p>Compilazione numero: #{response?counter}</p>
    <p>Data di compilazione: ${response.submissionDate}</p>
  </div>
  <div class="response">
    <#list response.answers as answer>
      <#assign question = answer.question />
      
      <p>#{answer?counter}. ${question.text?no_esc}</p>
      <#if question.note??><p>${question.note}</p></#if>
      <#if question.class.simpleName == "ShortTextQuestion">
          <input value="${answer.answer}" disabled/>
      <#elseif question.class.simpleName == "TextQuestion">
          <textarea value="${answer.answer}" disabled></textarea>
      <#elseif question.class.simpleName == "NumberQuestion">
          <input type="number" value="#{answer.answer}" disabled />
      <#elseif question.class.simpleName == "DateQuestion">
          <input type="date" value="${answer.answer}" disabled />
      <#elseif question.class.simpleName == "ChoiceQuestion">
          <fieldset style="border: none; position: relative;" <#if question.mandatory>data-required="required"</#if> data-min="${question.minNumberOfChoices}" data-max="${question.maxNumberOfChoices}" class="options">
              <#list question.options as option>
                  <#assign checked = answer.options?seq_contains(option) />
                  <label for=${question.code}-${option?counter}>${option.text}</label>
                  <input <#if question.maxNumberOfChoices == 1>type="radio"<#else>type="checkbox"</#if> disabled <#if checked>checked</#if> />
              </#list>
          </fieldset>    
      </#if>
    </#list>
  </div>
</#list>