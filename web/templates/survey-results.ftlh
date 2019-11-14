<i id="larrow" onclick="back()" class="arrow fas fa-arrow-left fa-2x"></i>  
<div class="opening">
    <h3>${survey.title}</h3>
    <p>Numero di compilazioni: #{survey.surveyResponses?size}</p>
</div>

<ul>
    <#list survey.questions as question>
        <li class="question card">
            <h4>#{question?counter} - ${question.text}</h4>
            <p>Numero di risposte: #{question.answers?size}</p>
        </li>

    </#list>

<i id="rarrow" onclick="next()" class="fas fa-arrow-right fa-2x"></i>

</ul>


<script type="text/javascript">
    document.getElementById("larrow").style.display = "block";
    document.getElementById("rarrow").style.display = "block";

    let pages = [];
    pages.push(...document.getElementsByClassName("opening"));
    pages.push(...document.getElementsByClassName("question"));
    
    for (let page of pages) {
        page.style.display = "none";
    }
    let currPage = 0;
    pages[currPage].style.display = "block";

    function back() {
        if (currPage > 0) {
            pages[currPage].style.display = "none";
            currPage--;
            pages[currPage].style.display = "block";
        }
    }

    function next() {
        if (currPage < pages.length - 1) {
            pages[currPage].style.display = "none";
            currPage++;
            pages[currPage].style.display = "block";
        }
    }
</script>