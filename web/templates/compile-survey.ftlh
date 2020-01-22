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
                                <label for=${question.code}-${option?counter}>${option.text}</label>
                                <input id="${question.code}-${option?counter}" <#if question.maxNumberOfChoices == 1>type="radio"<#else>type="checkbox"</#if> name="${question.code}" value="${option?index}"/>
                            </#list>
                              <p style="color: red"></p>
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

                if (currPage > 0 && currPage < pages.length - 1) {
                    validateQuestion(pages[currPage]);
                } else {
                    pages[currPage].style.display = "none";
                    currPage++;
                    pages[currPage].style.display = "flex";
                }
            }
        }

        function validateQuestion(question) {
            let input;
            for (let i of question.childNodes) {
                if (i.nodeName == "INPUT" || i.nodeName == "TEXTAREA" || i.nodeName == "FIELDSET") {
                    input = i;
                    break;
                }
            }

            // checkbox and radio
            if (input.nodeName == "FIELDSET") {
                let min = input.getAttribute('data-min');
                let max = input.getAttribute('data-max');
                let required = input.getAttribute('data-required') ? true : false;
                let selected = 0;
                for (let choice of input.childNodes) {
                    if (choice.nodeName == "INPUT" && choice.checked) {
                        selected++;
                    }
                }
                let valid = true;
                let errorMessage = "";
                
                if (required) {
                    if (selected < min) {
                        valid = false;
                        errorMessage = "Select at least " + min;
                        if (min == 1) {
                            errorMessage += " choice";
                        } else {
                            errorMessage += " choices";
                        }
                    }
                    if (selected > max) {
                        valid = false;
                        if (errorMessage != "") {
                            errorMessage += "\n";
                        }
                        errorMessage = "Select at most " + max;
                        if (max == 1) {
                            errorMessage += " choice";
                        } else {
                            errorMessage += " choices";
                        }
                    }
                } else {
                    if (selected == 0) {
                        let errorElement = input.getElementsByTagName('p')[0];
                        errorElement.innerHTML = "";
                        pages[currPage].style.display = "none";
                        currPage++;
                        pages[currPage].style.display = "flex";
                    } else {
                        if (selected < min) {
                            valid = false;
                            errorMessage = "Select at least " + min;
                            if (min == 1) {
                                errorMessage += " choice";
                            } else {
                                errorMessage += " choices";
                            }
                        }
                        if (selected > max) {
                            valid = false;
                            if (errorMessage != "") {
                                errorMessage += "\n";
                            }
                            errorMessage = "Select at most " + max;
                            if (max == 1) {
                                errorMessage += " choice";
                            } else {
                                errorMessage += " choices";
                            }
                        }
                    }
                }

                
                if (!valid) {
                    let errorElement = input.getElementsByTagName('p')[0];
                    errorElement.innerHTML = errorMessage;
                } else {
                    let errorElement = input.getElementsByTagName('p')[0];
                    errorElement.innerHTML = "";
                    pages[currPage].style.display = "none";
                    currPage++;
                    pages[currPage].style.display = "flex";
                }

            } else {
                if (input.checkValidity()) {
                    pages[currPage].style.display = "none";
                    currPage++;
                    pages[currPage].style.display = "flex";
                } else {
                    input.reportValidity();
                }
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