<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>

<div class="survey-info"> 
        <h4 class="text-center">${survey.title}</h4>
        <div class="icon icon--left">
            <#if survey.class.simpleName == "ReservedSurvey">
                <span class="fas fa-user-lock fa-4x"></span>
            <#else>
                <span class="fas fa-users fa-5x"></span>
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

<div class="survey-details">
    <div class="survey-questions">
        <h4 class="text-center">Domande</h4> 

        <#list survey.questions as question>
        <div class="question-item">
            #{question?counter} - ${question.text?no_esc}<#if question.mandatory>*</#if>
            <div class="buttons">
                
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


    <#if survey.class.simpleName == "ReservedSurvey">
    <div class="survey-participants">
        <h4 class="text-center">Partecipanti</h4>
        
        <h5 class="text-center"> Aggiungi partecipante </h5>
        <form action="/pollweb/participants" method="post">
            <label>Email <input type="email" name="email"></label>
            
            <label>Password <input type="password" name="password"/></label>
             
            <input type="hidden" name="survey" value="#{survey.id}">
            <input type="submit" class="small-btn" name="add" value="Aggiungi partecipante">
        </form>
        <hr>
        <h5 class="text-center">Importa partecipanti da CSV</h5>
        <form action="/pollweb/participants" method="post" enctype="multipart/form-data"><br>
            <input class="small-btn" type="reset" value="Reset"/>
            <input type="file" class="small-btn" name="csv"/>
            <input type="hidden" name="survey" value="#{survey.id}"/>
            <input type="submit" class="small-btn" name="add-from-file" value="Conferma"/>
        </form>

        <div class="participants">
            
            <#list survey.participants as participant>
                <div class="participant">
                    ${participant.email}
                    <form style="display: inline-block;" action="/pollweb/participants" method="post">
                        <input type="hidden" name="survey" value="#{survey.id}">
                        <input type="hidden" name="participant" value="#{participant.id}">
                        <input type="hidden" name="delete">
                        <button onclick="removeParticipant(this.form);" class="delete-btn" type="button" > 
                            <i class="fas fa-times"></i>
                        </button>
                    </form>
                </div>      
            </#list>
        </div>
    </div>
    </#if>
</div>

<script>
    function deleteQuestion(form) {
        if(window.confirm("Sei sicuro di voler cancellare la domanda?")) {
            form.submit();
        } else {
            //
        }
    }

    function removeParticipant(form) {
        if(window.confirm("Sei sicuro di voler rimuovere il participante?")) {
            form.submit();
        } else {
            //
        }
    }
</script>