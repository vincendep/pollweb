<#assign openSurveys = surveys?filter(survey -> survey.active)>
<div>
    <section>
        <h3>Informazioni personali</h3>
        <ul>
            <li>Nome: ${logged_user.name}</li>
            <li>Cognome: ${logged_user.surname}</li>
            <li>Email: ${logged_user.email}</li>
            <li>Sondaggi pubblicati: ${surveys?size}</li>
            <li>Sondaggio aperti: #{openSurveys?size}</li>
            <li>Sondaggi chiusi: #{surveys?size - openSurveys?size}</li>
    </section>
    </section>
    </hr>
    <section>
        <h3>Sondaggi gestiti</h3>
        <ul>
            <#list surveys?sort_by("pubblicationDate")?reverse as survey>
                <li><a href="account/survey-details?n=#{survey.id}">${survey.title}</a> | status: <#if survey.active>Aperto<#else>Chiuso</#if> |
                    data di pubblicazione: ${survey.pubblicationDate} 
                    <form action="surveys" method="post">
                        <input type="hidden" name="n" value="${survey.id}"/>
                        <input type="submit" <#if !survey.active>name="open" value="Apri sondaggio"<#else>name="close" value="Chiudi sondaggio"</#if>"/>
                    </form>
                </li>
            </#list>
        </ul>
    </section>
    <a href="account/create-survey">Crea sondaggio</a>
</div>