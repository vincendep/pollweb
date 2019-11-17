<section>
	<div class="title">
	    <h3>Tutti i sondaggi</h3>
	    <p>Lista completa di tutti i sondaggi pubblicati su Pollweb</p>
    </div>
    <ul class="list">
        <#list surveys?sort_by("pubblicationDate") as survey>
            <li>
            	<a class="item" href="compile-survey?survey=#{survey.id}">
	            	<div class="icon icon--left">
                            <#if survey.class.simpleName == "ReservedSurvey">
                                <span class="fas fa-user-lock fa-2x"></span>
                            <#else>
                                <span class="fas fa-users fa-2x"></span>
                            </#if>
	            	</div>
	            	<div class="text">
		                <h4 class="head">${survey.getTitle()}</h4>
		                <p class="info">Responsabile: ${survey.manager.name} ${survey.manager.surname}</p> 
		                <p class="info">Data di pubblicazione: ${survey.pubblicationDate}</p>
	            	</div>
	            	<div class="icon icon--right"><i class="fas fa-greater-than fa-2x"></i></div>
            	</a>
            </li>
        </#list>
    </ul>
</section>