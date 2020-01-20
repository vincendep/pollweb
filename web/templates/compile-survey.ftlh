<div id="compile-survey">
    <i id="larrow" onclick="back()" class="arrow fas fa-arrow-left fa-2x"></i>
        <div id="survey">
        <div id="opening" class="page opening" style="flex-direction: column;">
            <h2>${survey.title}</h2>
            <p>${survey.openingText}</p>
        </div>
        <form id="survey-form" action="/pollweb/compile-survey-controller?survey=${survey.id}" method="get">
            <input type="hidden" name="survey" value="#{survey.id}">
            <#list survey.questions as question>
                <section class="page question-card">
                    <h4>#{question?counter}. ${question.text?no_esc}</h4>
                    <#if question.note??><p>${question.note}</p></#if>
                    <#if question.class.simpleName == "ShortTextQuestion">
                        <input <#if question.mandatory>required="required"</#if> minlength="#{question.minLength}" maxlength="#{question.maxLength}" pattern="${question.pattern}" type="text" name="${question.code}"/>
                    <#elseif question.class.simpleName == "TextQuestion">
                        <textarea <#if question.mandatory>required="required"</#if> minlength="#{question.minLength}" maxlength="#{question.maxLength}" rows="4" name="${question.code}"></textarea>
                    <#elseif question.class.simpleName == "NumberQuestion">
                        <input <#if question.mandatory>required="required"</#if> min="${question.minValue}" max="${question.maxValue}" type="number" name="${question.code}"/>
                    <#elseif question.class.simpleName == "DateQuestion">
                        <input <#if question.mandatory>required="required"</#if> type="date" min="${question.minDate}" max="${question.maxDate}" name="${question.code}"/>
                    <#elseif question.class.simpleName == "ChoiceQuestion">
                        <fieldset style="border: none;" <#if question.mandatory>data-required="required"</#if> data-min="${question.minNumberOfChoices}" data-max="${question.maxNumberOfChoices}" class="options">
                            <#list question.options as option>
                                <label>
                                    <input <#if question.maxNumberOfChoices == 1>type="radio"<#else>type="checkbox"</#if> name="${question.code}" value="${option?index}"/> ${option.text}
                                </label>
                            </#list>
                        </fieldset>
                    </#if>
                </section>
            </#list>
        </form>
        <div id="closing" class="page closing" style="flex-direction: column;">
            <p>${survey.closingText}</p>
            <button form="survey-form" class="btn" type="button" onclick="submitSurvey(this.form)">Conferma</button>
        </div>
    </div>
    <i id="rarrow" onclick="next()" class="fas fa-arrow-right fa-2x"></i>
    <script type="text/javascript">
        document.getElementById("larrow").style.display = "block";
        document.getElementById("rarrow").style.display = "block";
        var pages = [];
        pages.push(document.getElementById("opening"))
        
        for (let page of document.getElementsByTagName("section")) {
            pages.push(page)
        }
        
        pages.push(document.getElementById("closing"))
        

        for (let page of pages) {
            page.style.display = "none";
        }
        var currPage = 0;
        pages[currPage].style.display = "flex";

        function back() {
            if (currPage > 0) {
                pages[currPage].style.display = "none";
                currPage--;
                pages[currPage].style.display = "flex";
            }
        }

        function next() {
            if (currPage < pages.length - 1) {
                pages[currPage].style.display = "none";
                currPage++;
                pages[currPage].style.display = "flex";
            }
        }

        function submitSurvey(form) {
            let validity = true;
            for (let input of form.elements) {
                validity = validity && input.checkValidity();
            }
            if (validity) {
                form.submit();
            } else {
                window.alert("Compilazione non valida!");
                form.reportValidity();
            }
        }
    </script>
</div>