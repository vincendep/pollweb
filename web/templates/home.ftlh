<section>
    <h3>Tutti i sondaggi</h3>
    <ul>
        <#list surveys?sort_by("pubblicationDate") as survey>
            <li>
                <a href="survey?id=#{survey.id}">${survey.getTitle()}</a> - responsabile: ${survey.manager.name} ${survey.manager.surname}
            </li>
        </#list>
    </ul>
</section>