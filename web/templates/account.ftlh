 <#assign openSurveys = surveys?filter(survey -> survey.active)>
    <div class="account-content">
        <div class="top-cards">
            <div class="info-utente">
                <h3>Informazioni personali</h3>
                <div class="account-container">
                    <img class="avatar" alt="" src="/pollweb/images/avatar.png" height="150">
                    <ul>
                        <li>Nome: ${logged_user.name}</li>
                        <li>Cognome: ${logged_user.surname}</li>
                        <li>Email: ${logged_user.email}</li>
                        <li>Sondaggi pubblicati: ${surveys?size}</li>
                        <li>Livello utente: <#if logged_user.role == "RESPONSIBLE">Responsabile<#else>Amministratore</#if></li>
                    </ul>
                </div>
                <div style="text-align: center;">
                    <form style="display: inline-block;" action="/pollweb/auth">
                        <input class="btn" type="submit" name="logout" value="Logout"/>
                    </form>
                </div>
            </div>
            <#if logged_user.role == "ADMINISTRATOR">
            <div class="card-utenti">
                <h3>Utenti registrati</h3>
                <p style="font-size: 5em; margin: 0;">#{users_size}</p>
                <form style="display: inline-block;" action="account/manage-users" method="get">
                    <input class="btn" type="submit" value="Gestisci utenti"/>
                </form>
            </div>
            </#if>
        </div>

        <section class="my-surveys">
            <h3>Sondaggi gestiti</h3>
            <table>
                <thead>
                    <tr>
                        <td><strong>Titolo</strong></td>
                        <td class="status"><strong>Status</strong></td>
                        <td class="tipo"><strong>Tipo</strong></td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                <#list surveys?sort_by("pubblicationDate")?reverse as survey>
                <tr>   
                    <td><a href="account/survey-details?survey=#{survey.id}">${survey.title} <i class="fas fa-edit"></i></a></td>
                    <td class="status"><#if survey.active>Aperto<#else>Chiuso</#if></td>
                    <td class="tipo"><#if survey.class.simpleName == "ReservedSurvey">Riservato<#else>Pubblico</#if></td>
                    <td class="trash-icon">
                        <form style="text-align: center;" action="/pollweb/surveys" method="post">
                            <input type="hidden" name="survey" value="#{survey.id}">
                            <input type="hidden" name="delete">
                            <button class="delete-btn" type="button" onclick="deleteSurvey(this.form)">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </form></td>
                </tr>
                </#list>
            </tbody>
        </table>
        <a class="btn" href="account/create-survey">Crea sondaggio</a>
        </section>
    </div>
    <script>
        function deleteSurvey(form) {
            if (window.confirm("Sei sicuro di voler eliminare il sondaggio?")) {
                form.submit();
            } else {
                
            }
        }
    </script>