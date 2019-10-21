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
    <form action="create-question" method="get">    
        <fieldset>
            <legend>Aggiungi domanda</legend>
            <input type="hidden" name="survey" value="#{survey.id}"/>
            <select name="type">
                <option value="long text">Testo lungo</option>
                <option value="choice">Scelta multipla</option>
                <option value="number">Numerica</option>
                <option value="date">Data</option>
                <option value="short text">Testo breve</option>
            </select> 
            <input type="submit" name="add" value="Aggiungi"/>
        </fieldset>
    </form>
</section>
<#if survey.class.simpleName == "ReservedSurvey">
    <section>
        <h4>Partecipanti</h4>
        <p>Numero di participanti: #{survey.participants?size}</p>
        <ul>
            <#list survey.participants as participant>
                <li>${participant.email}
                    <form action="/pollweb/participants" method="post">
                        <input type="hidden" name="survey" value="#{survey.id}">
                        <input type="hidden" name="n" value="#{participant.id}">
                        <button type="submit" name="delete">
                            <i class="fas fa-times"></i>
                        </button>
                    </form>
                </li>
            </#list>
        </ul>
        <form action="/pollweb/participants" method="post">
            <fieldset>
                <legend>Aggiungi partecipante</legend>
                <label>Email: 
                    <input type="email" name="email"/></br>
                </label>
                <label>Password: 
                    <input type="password" name="password"/></br>
                </label>
                <input type="hidden" name="survey" value="#{survey.id}"/>
                <input type="submit" name="add" value="Aggiungi partecipante"/>
            </fieldset>
        </from>
        <form action="/pollweb/participants" method="post" enctype="multipart/form-data">
            <fieldset>
                <legend>Importa partecipanti da CSV</legend>
                <input type="file" name="csv"/></br>
                <input type="hidden" name="survey" value="#{survey.id}"/>
                <input type="submit" name="add-from-file" value="Aggiungi partecipanti"/>
            </fieldset>
        </form>
    </section>
</#if>