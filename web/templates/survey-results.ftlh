<!-- evita hibernate proxy -->
<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>

<div class="opening">
    <h3>${survey.title}</h3>
    <p>Numero di compilazioni: #{survey.surveyResponses?size}</p>
</div>

<#list survey.questions as question>  
  <div class="card">
    ${question.class.simpleName}
    <div class="card-header">
      <h4>#{question?counter} - ${question.text}</h4>
      <p>Numero di risposte: #{question.answers?size}</p>
    </div>
    <div class="card-body">
      <#if question.class.simpleName == "ChoiceQuestion">
        <#list question.options as option>
          <#assign optCount = 0>
          <#list question.answers as answer>
            <#list answer.options as opt>
              <#if option == opt>
                <#assign optCount = optCount + 1>
              </#if>
            </#list>
          </#list>
          <div>${option.text} - #{optCount}</div>
        </#list>
      <#else>
        <#list question.answers as answer>
          <span>${answer.answer}</span>
        </#list>
      </#if>
    </div>
  </div>
</#list>