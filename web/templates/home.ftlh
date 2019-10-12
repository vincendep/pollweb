<section>
    <h3>All Surveys</h3>
    <ul>
        <#list surveys?sort_by("pubblicationDate") as survey>
            <li>
                <a href="compile-survey?id=#{survey.id}">${survey.getTitle()}</a>
            </li>
        </#list>
    </ul>
</section>