<#assign openSurveys = surveys?filter(survey -> survey.active)>
<div class="account-content">
    <section class="info-utente">
        <form action="/pollweb/auth"><input class="btn" type="submit" name="logout" value="Logout"/></form>
        <h3>Informazioni personali</h3>
        <ul>
            <li>Nome: ${logged_user.name}</li>
            <li>Cognome: ${logged_user.surname}</li>
            <li>Email: ${logged_user.email}</li>
            <li>Sondaggi pubblicati: ${surveys?size}</li>
            <li>Sondaggio aperti: #{openSurveys?size}</li>
            <li>Sondaggi chiusi: #{surveys?size - openSurveys?size}</li>
        </ul>
    </section>
    <section class="my-surveys">
        <h3>Sondaggi gestiti</h3>
        <table>
            <thead>
                <tr>
                    <td><strong>Titolo</strong></td>
                    <td><strong>Status</strong></td>
                    <td><strong>Data di pubblicazione</strong></td>
                </tr>
            </thead>
            <tbody>
            <#list surveys?sort_by("pubblicationDate")?reverse as survey>
            <tr>
                <td><a href="account/survey-details?n=#{survey.id}">${survey.title}</a></td>
                <td><#if survey.active>Aperto<#else>Chiuso</#if></td>
                <td>${survey.pubblicationDate}</td>
                 <td>
                    <form action="surveys" method="post">
                        <input type="hidden" name="n" value="${survey.id}"/>
                        <input class="btn btn--small"  type="submit" <#if !survey.active>name="open" value="Apri sondaggio"<#else>name="close" value="Chiudi sondaggio"</#if>"/>
                        <input class="btn btn--small btn--danger" type="submit" name="delete" value="Elimina"/>
                    </form>
                    </td>
                </tr>
            </#list>
        </tbody>
        </td>
    </table>
    <a class="btn" href="account/create-survey">Crea sondaggio</a>
    </section>
</div>