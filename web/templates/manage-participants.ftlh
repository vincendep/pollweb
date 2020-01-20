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
</div>
<div style="margin-top: 2em">
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

<script>
    function removeParticipant(form) {
        if(window.confirm("Sei sicuro di voler rimuovere il partecipante?")) {
            form.submit();
        } else {
            //
        }
    }
</script>