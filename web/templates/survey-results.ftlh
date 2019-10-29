<h3>${survey.title}</h3>
<p>Numero di compilazioni: #{survey.surveyResponses?size}</p>
<ul>
    <#list survey.questions as question>
        <li>
            <h4>#{question?counter} - ${question.text}</h4>
            <p>Numero di risposte: #{question.answers?size}</p>
        </li>
    </#list>
</ul>