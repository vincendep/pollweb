<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>
<div style="display: flex; justify-content: center; flex-wrap: wrap;">
<div class="survey-info"> 
    <h4 class="text-center">${survey.title}</h4>
    <div class="survey-image" style="display: flex; justify-content: space-around;">
        <div class="icon icon--left">
            <#if survey.class.simpleName == "ReservedSurvey">
                <span id="survey-image" class="fas fa-user-lock fa-4x"></span>
            <#else>
                <span id="survey-image" class="fas fa-users fa-5x"></span>
            </#if>
        </div>
        <div>
            <p>Numero di domande: #{survey.questions?size}</p>
            <p>Numero di risposte ricevute: #{survey.surveyResponses?size}</p>
            <p>Status attuale: <#if survey.active>Aperto<#else>Chiuso</#if></p>
            <p>Tipo: <#if survey.class.simpleName == "ReservedSurvey">Riservato<#else>Pubblico</#if></p>
            <div class="text-center" style="display: inline-block;">
                <form style="display:inline-block;" action="survey-results" method="get">
                    <input type="hidden" name="survey" value="#{survey.id}"/>
                    <input type="submit" class="small-btn" value="Mostra risultati"/>
                </form>
                
                <form style="display:inline-block;" action="/pollweb/surveys" method="get">
                    <input type="hidden" name="survey" value="#{survey.id}"/>
                    <input type="submit" class="small-btn" name="download" value="Download risultati"/>
                </form>

                <form style="display: inline-block;" action="/pollweb/surveys" method="post">
                    <input type="hidden" name="survey" value="#{survey.id}"/>
                    <input type="submit" class="small-btn" 
                        <#if survey.active>
                            name="close" value="Chiudi" />
                        <#else>
                            name="open" value="Apri"/>
                        </#if>
                </form>
            </div>
        </div>
    </div>
</div>


<#if survey.class.simpleName == "ReservedSurvey">
<div class="text-center participants">
    <h4 class="text-center">Partecipanti</h4>
    
    <p style="font-size: 5em;">#{survey.participants?size}</p>
    <form method="get" action="/pollweb/manage-participants" >
        <input type="hidden" name="survey" value="#{survey.id}">
        <input type="submit" class="btn" value="Gestisci partecipanti">
    </form>
</div>
</#if>
</div>

<div style="text-align: center; margin-bottom: 3em;" class="survey-address">
    <h5>Condividi il link per raccogliere risposte</h5>
    <div style="display: flex; align-items: center; justify-content: center; position: relative;">
        <textarea id="survey-address" rows="1" style="resize: none; display: inline-block; width: 75%;text-align: center;height: auto;">http://localhost:8080/pollweb/compile-survey?survey=#{survey.id}</textarea>
        <i id="copy-icon" style="display: inline-block; cursor: pointer; margin: 0 1em;" class="fas fa-copy"></i>
        <div id="copy-text" style="border-radius: 5px; display: none;padding: 0.5em;background-color: #333;color: white;position: absolute; left: 48%; top: 100%; margin-top: 1em;">Copiato!</div>
    </div>
</div>

<div class="survey-questions">
    <h4 class="text-center">Domande</h4> 
    <#list survey.questions as question>
    <div class="question-item">
        #{question?counter} - ${question.text?no_esc}<#if question.mandatory>*</#if>
        <div class="buttons">           
            <form style="display: inline-block;" action="/pollweb/questions" method="post">
                <input type="hidden" name="survey" value="#{survey.id}"/>
                <input type="hidden" name="question-index" value="#{question?index}"/>
                <input type="hidden" name="delete">
                <button type="button" class="delete-btn" onclick="deleteQuestion(this.form)">
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
        </div>
    </div>
    </#list>   
    <p class="text-center"><small>*L'asterisco identifica le domande obbligatorie</small></p>
    <form class="text-center" action="create-question" method="get" ><br>    
        <h5 class="text-center"> Aggiungi domanda </h5>
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
</div>


<script>
    let surveyAddress = document.getElementById('survey-address');
    let copyIcon = document.getElementById('copy-icon');
    let copyText = document.getElementById('copy-text');
    copyIcon.onclick = function() {
        surveyAddress.select();
        document.execCommand('copy');
        copyText.style.display = 'block';
        setTimeout(function() {
            copyText.style.display = 'none';
        } , 1500);
    }

    function deleteQuestion(form) {
        if(window.confirm("Sei sicuro di voler cancellare la domanda?")) {
            form.submit();
        } else {
            //
        }
    }
</script>