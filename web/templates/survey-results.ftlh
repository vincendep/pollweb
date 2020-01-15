<!-- evita hibernate proxy -->
<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>

<div class="opening">
    <h3>${survey.title}</h3>
    <p>Numero di compilazioni: #{survey.surveyResponses?size}</p>
</div>

<#list survey.questions as question>  
  <div class="card">
    <div class="card-header" onclick="toggle(this)">
        <h4 class="question-text">#{question?counter} - ${question.text}</h4>
        <i class="fas fa-caret-down"></i>
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
          <div>${option.text} : #{optCount}</div>
        </#list>
      <#else>
        <#list question.answers as answer>
          <span>${answer.answer}</span>
        </#list>
      </#if>
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
