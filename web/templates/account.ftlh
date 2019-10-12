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
        <h3>Your Surveys</h3>
        <ul>
            <#list surveys?sort_by("pubblicationDate") as survey>
                <li><a href="account/survey/results?n=#{survey.id}">${survey.title}</a> | status: <#if survey.active>Aperto<#else>Chiuso</#if> |
                    data di pubblicazione: ${survey.pubblicationDate} </li>
            </#list>
        </ul>
    </section>
    <a href="account/create-survey">add new survey</a>
</div>