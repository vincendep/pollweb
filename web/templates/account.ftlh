    <#assign openSurveys = surveys?filter(survey -> survey.active)>
    <div class="account-content">
        <section class="info-utente">
            <h3>Informazioni personali</h3>
            <ul>
                <li>Nome: ${logged_user.name}</li>
                <li>Cognome: ${logged_user.surname}</li>
                <li>Email: ${logged_user.email}</li>
                <li>Sondaggi pubblicati: ${surveys?size}</li>
                <li>Sondaggio aperti: #{openSurveys?size}</li>
                <li>Sondaggi chiusi: #{surveys?size - openSurveys?size}</li>
            </ul>
            <form action="/pollweb/auth"><input class="btn" type="submit" name="logout" value="Logout"/></form>
        </section>
        <section class="my-surveys">
            <h3>Sondaggi gestiti</h3>
            <table>
                <thead>
                    <tr>
                        <td><strong>Titolo</strong></td>
                        <td class="status"><strong>Status</strong></td>
                        <td class="pubblication-date"><strong>Data di pubblicazione</strong></td>
                    </tr>
                </thead>
                <tbody>
                <#list surveys?sort_by("pubblicationDate")?reverse as survey>
                <tr>   
                    <td><a href="account/survey-details?n=#{survey.id}">${survey.title} <i class="fas fa-edit"></i></a></td>
                    <td class="status"><#if survey.active>Aperto<#else>Chiuso</#if></td>
                    <td class="pubblication-date">${survey.pubblicationDate}</td>
                    <td class="trash-icon">
                        <form action="/pollweb/surveys" method="post">
                            <input type="hidden" name="n" value="#{survey.id}">
                            <button type="submit" name="delete">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </form></td>
                </tr>
                </#list>
            </tbody>
            </td>
        </table>
        <a class="btn" href="account/create-survey">Crea sondaggio</a>
        </section>
    </div>