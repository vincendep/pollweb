<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>
<h3>${survey.title}</h3>
<p>Numero di domande: #{survey.questions?size} (#{mandatoryQuestions?size} obbligatorie)</p>
<p>Numero di compilazioni: #{survey.surveyResponses?size}</p>
<section>
    <h4>Domande:</h5>
    <ol>
        <#list survey.questions as question>
            <li>
                ${question.text} - <#if question.mandatory>(obbligatoria)</#if>
                <form action="/pollweb/questions" method="post">
                    <input type="hidden" name="survey" value="#{survey.id}"/>
                    <input type="hidden" name="question-index" value="#{question?index}"/>
                    <input type="submit" name="delete" value="Rimuovi"/>
                </form>
            </li>
        </#list>
    </ol>
    <form action="create-question" method="post">
        <input type="hidden" name="survey" value="#{survey.id}"/>
        <select name="type">
            <option value="long text">Testo lungo</option>
            <option value="choice">Scelta multipla</option>
            <option value="number">Numerica</option>
            <option value="date">Data</option>
            <option value="short text">Testo breve</option>
        </select> 
        <input type="submit" name="add" value="Aggiungi domanda"/>
    </form>
</section