<div id="compile-survey">
    <i id="larrow" onclick="back()" class="arrow fas fa-arrow-left fa-2x"></i>
        <div id="survey">
        <div class="opening" style="flex-direction: column;">
            <h2>${survey.title}</h2>
            <p>${survey.openingText}</p>
        </div>
        <form id="survey-form" action="/pollweb/compile-survey-controller?survey=${survey.id}" method="post">
            <input type="hidden" name="survey" value="#{survey.id}">
            <#list survey.questions as question>
                <div class="question-card">
                    <h4>#{question?counter}. ${question.text}</h4>
                    <#if question.note??><p>${question.note}</p></#if>
                    <#if question.class.simpleName == "ShortTextQuestion">
                        <input type="text" name="${question.code}"/>
                    <#elseif question.class.simpleName == "TextQuestion">
                        <textarea rows="4" name="${question.code}"></textarea>
                    <#elseif question.class.simpleName == "NumberQuestion">
                        <input type="number" name="${question.code}"/>
                    <#elseif question.class.simpleName == "DateQuestion">
                        <input type="date" name="${question.code}"/>
                    <#elseif question.class.simpleName == "ChoiceQuestion">
                        <div class="options">
                            <#list question.options as option>
                                <label>
                                    <input <#if question.maxNumberOfChoices == 1>type="radio"<#else>type="checkbox"</#if> name="${question.code}" value="${option?index}"/> ${option.text}
                                </label>
                            </#list>
                        </div>
                    </#if>
                </div>
            </#list>
        </form>
        <div class="closing" style="flex-direction: column;">
            <p>${survey.closingText}</p>
            <input form="survey-form" class="btn" type="submit" name="submit" value="Conferma"  />
        </div>
    </div>
    <i id="rarrow" onclick="next()" class="fas fa-arrow-right fa-2x"></i>
    <script type="text/javascript">
        document.getElementById("larrow").style.display = "block";
        document.getElementById("rarrow").style.display = "block";

        let pages = [];
        pages.push(...document.getElementsByClassName("opening"));
        pages.push(...document.getElementsByClassName("question-card"));
        pages.push(...document.getElementsByClassName("closing"));
        
        for (let page of pages) {
            page.style.display = "none";
        }
        let currPage = 0;
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
    </script>
</div>