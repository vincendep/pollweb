<div class="total">
<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>
<div>
    <h3>${survey.title}</h3>
<div class="border">
        <p>Numero di domande: #{survey.questions?size} (#{mandatoryQuestions?size} obbligatorie)</p>
        <p>Numero di risposte: #{survey.surveyResponses?size}</p>
<form style="display:inline-block;" action="survey-results" method="get">
    <input type="hidden" name="survey" value="#{survey.id}"/>
    <input type="submit" class="small-btn" value="Mostra risultati"/>
</form>
<form style="display:inline-block;" action="/pollweb/surveys" method="get">
    <input type="hidden" name="survey" value="#{survey.id}"/>
    <input type="submit" class="small-btn" name="download" value="Download risultati"/>
</form>
</div>
   
<h4 class="queston-center">Domande:</h4>
<div class="question">
    <ol>
    <#list survey.questions as question>
        <li>
            ${question.text} - <#if question.mandatory>(obbligatoria)</#if>
            <form style="display: inline-block;" action="/pollweb/questions" method="post">
                <input type="hidden" name="survey" value="#{survey.id}"/>
                <input type="hidden" name="question-index" value="#{question?index}"/>
                <button type="submit" class="delete-btn" name="delete">
                    <i class="fas fa-trash-alt"></i>
                </button>
            </form>
            <form style="display: inline-block;" action="/pollweb/questions" method="post">
                <input type="hidden" name="survey" value="#{survey.id}"/>
                <input type="hidden" name="question-index" value="#{question?index}"/>
                <button class="arrow-btn" type="submit" name="down">
                    <i class="fas fa-arrow-down"></i>
                </button>
            </form>
            <form style="display: inline-block;" action="/pollweb/questions" method="post">
                <input type="hidden" name="survey" value="#{survey.id}"/>
                <input type="hidden" name="question-index" value="#{question?index}"/>
                <button class="arrow-btn" type="submit" name="up">
                    <i class="fas fa-arrow-up"></i>
                </button> 
            </form>
        </li> 
    </#list>
    </ol>
</div>
<div class="border">
    <form  action="create-question" method="get" ><br>    
            Aggiungi domanda <br>
            <input type="hidden" name="survey" value="#{survey.id}"/>
            <select name="type">
                <option value="long text">Testo lungo</option>
                <option value="choice">Scelta multipla</option>
                <option value="number">Numerica</option>
                <option value="date">Data</option>
                <option value="short text">Testo breve</option>
            </select> 
            <input type="submit" class="small-btn" value="Aggiungi"/>
        
    </form>
</div><br>

<hr class="lines1">


<#if survey.class.simpleName == "ReservedSurvey">
        <h4 class="title">Partecipanti</h4>
        <p class="title">Numero di participanti: #{survey.participants?size}</p>
        <div class="question">
            <ul>
                <#list survey.participants as participant>
                    <li>${participant.email}
                        <form style="display: inline-block;" action="/pollweb/participants" method="post">
                            <input type="hidden" name="survey" value="#{survey.id}">
                            <input type="hidden" name="participant" value="#{participant.id}">
                            <button class="delete-btn" type="submit" name="delete"> 
                                <i class="fas fa-times"></i>
                            </button>
                        </form> <br><br>
                    </li>
                </#list>
            </ul>
        </div>
<hr class="lines1"><br>

<div class="border">
        <form action="/pollweb/participants" method="post"><br>
                Aggiungi partecipante<br>
                <label>Email: &nbsp; &nbsp; &nbsp;
                    <input type="email" name="email"/>
                </label><br>
                <label>Password: 
                    <input type="password" name="password"/><br>
                </label><br>
                <input type="hidden" name="survey" value="#{survey.id}"/><br>
                <input type="submit" class="small-btn" name="add" value="Aggiungi partecipante"/>
        </form>
</div> <br>
        <div class="border">
        <form action="/pollweb/participants" method="post" enctype="multipart/form-data"><br>
                Importa partecipanti da CSV<br><br>
                <input class="small-btn" type="reset" value="Reset"/>&nbsp;
                <input type="file" name="csv"/><br>
                <input type="hidden" name="survey" value="#{survey.id}"/><br>
                <input type="submit" class="small-btn" name="add-from-file" value="Aggiungi partecipanti"/>
        </form>
</div> <br>
</#if>
<div class="title">
Status: 
    <#if survey.active>
        Aperto <form action="/pollweb/surveys" method="post">
            <input type="hidden" name="survey" value="#{survey.id}"/>
            <input type="submit" class="btn" name="close" value="Chiudi"/>
        </form>
    <#else>
        Chiuso <form action="/pollweb/surveys" method="post">
            <input type="hidden" name="survey" value="#{survey.id}"/>
            <input type="submit" class="btn" name="open" value="Apri"/>
        </form>
    </#if> 
</div>
</div>
</div>