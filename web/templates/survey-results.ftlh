<!-- evita hibernate proxy -->
<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>
<!--
<#list survey.surveyResponses as response>
    <div class="response-header">

        <p><h2 class="text-center"><strong>Compilazione numero: #{response?counter}</strong></h2></p>
        <p>Data di compilazione: ${response.submissionDate}</p>
    </div>
    <div class="response">
    <#list response.answers as answer>
      <#assign question = answer.question />
      
      <p> <h3><strong>#{answer?counter}. ${question.text?no_esc}</strong></h3></p>
      <#if question.note??><p><h3>${question.note}</h3></p></#if>
      <#if question.class.simpleName == "ShortTextQuestion">
          <input value="${answer.answer}" disabled/>
      <#elseif question.class.simpleName == "TextQuestion">
          <textarea value="${answer.answer}" disabled ></textarea>
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

</div>
</#list>
!-->
<#list survey.surveyResponses as response>
    <div class="card">
    <div class="card-header" onclick="toggle(this)">    
        <p><h2 class="text-center"><strong>Compilazione numero: #{response?counter}</strong></h2></p>
        <p>Data di compilazione: ${response.submissionDate}</p>
        <i class="fas fa-caret-down"></i>
    </div>
    <div class="card-body">


    <#list response.answers as answer>
      <#assign question = answer.question />
      
      <p> <h3><strong>#{answer?counter}. ${question.text?no_esc}</strong></h3></p>
      <#if question.note??><p><h3>${question.note}</h3></p></#if>
      <#if question.class.simpleName == "ShortTextQuestion">
          <input value="${answer.answer}" disabled/>
      <#elseif question.class.simpleName == "TextQuestion">
          <textarea value="${answer.answer}" disabled class="resize" ></textarea>
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
    

    </div>
</#list>
<script type="text/javascript">
let cards = document.getElementsByClassName('card-body');
for (let card of cards) {
    card.style.display = 'none';
}

function toggle(element){
    let card = element.nextElementSibling;
  if (card.style.display == 'none') {
    card.style.display = 'block';
  } else {
        card.style.display = 'none';
  }
}
</script>
