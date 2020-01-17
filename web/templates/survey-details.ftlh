<#assign mandatoryQuestions = survey.questions?filter(question -> question.mandatory)>

<div class="survey-info"> 
    <h3>${survey.title}</h3>

    <p>Numero di domande: #{survey.questions?size}</p>
    <p>Numero di domande obbligatorie: #{mandatoryQuestions?size}</p>
    <p>Numero di risposte ricevute: #{survey.surveyResponses?size}</p>
    <p>Status attuale: <#if survey.active>Aperto<#else>Chiuso</#if></p>
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
                    name="close" value="Chiudi"
                <#else>
                    name="open" value="Apri"/>
                </#if>
        </form>
</div>

<div class="survey-questions">
    <h2 class="title1">Domande:</h2>  
    <#list survey.questions as question>
        
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
            </form><br>
       
    </#list>
    

    <form  action="create-question" method="get" ><br>    
            <h2 class="title1"> Aggiungi domanda </h2> <br>
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
<div class="split">
<div class="border">  
<div class="border">

        <h2 class="title1">Partecipanti</h2>
        <h3 class="title1">Numero di participanti: #{survey.participants?size}</h3>
        <div class="question">
            
                <#list survey.participants as participant>
                    ${participant.email}
                        <form style="display: inline-block;" action="/pollweb/participants" method="post">
                            <input type="hidden" name="survey" value="#{survey.id}">
                            <input type="hidden" name="participant" value="#{participant.id}">
                            <input type="hidden" name="delete">
                            <button onclick="removeParticipant(this.form);" class="delete-btn" type="button" > 
                                <i class="fas fa-times"></i>
                            </button>
                        </form><br>
                   
                </#list>
        </div> 
        <h2 class="title1"> Aggiungi partecipante </h2>
        <form action="/pollweb/participants" method="post">
            
<div class="container">
            <div class="row">
                <div class="col-25">
                <label>Email: </label>
                </div>
                <div class="col-75">
                    <input type="email" name="email"/>
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                <label>Password: </labe>
                </div>
                <div class="col-75">
                    <input type="password" name="password"/>
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                <input type="hidden" name="survey" value="#{survey.id}"/><br>
                </div>
            </div>
</div>
        <input type="submit" class="small-btn" name="add" value="Aggiungi partecipante"/>
              
        </form>

        <h2 class="title1">Importa partecipanti da CSV</h2>
        <form action="/pollweb/participants" method="post" enctype="multipart/form-data"><br>
                <input class="small-btn" type="reset" value="Reset"/>&nbsp;
                <input type="file" class="small-btn" name="csv"/><br>
                <input type="hidden" name="survey" value="#{survey.id}"/><br>
                <input type="submit" class="small-btn" name="add-from-file" value="Aggiungi partecipanti"/>
        </form>


</div>
<div class="clear"></div>
</#if>
</div>

<script type="text/javascript">
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