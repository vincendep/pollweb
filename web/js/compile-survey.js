let leftArrow = document.getElementById("larrow");
let rightArrow = document.getElementById("rarrow");
leftArrow.style.display = "block";
rightArrow.style.display = "block";
leftArrow.onclick = back;
rightArrow.onclick = next;

var currPage = 0;
var pages = getSurveyPages();
for (let i = 1; i < pages.length; i++) {
    pages[i].style.display = "none";
}

function getSurveyPages() {
    let pages = [];
    pages.push(document.getElementById("opening"));
    for (let i = 0; i < document.getElementsByTagName("section").length; i++) {
        pages.push(document.getElementsByTagName("section")[i]);
    }
    pages.push(document.getElementById("closing"));
    return pages;
}

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

function validateQuestion(sectionElement) {
    let formElement;
    for (let i = 0; i < sectionElement.childNodes.length; i++) {
        let element = sectionElement.childNodes[i];
        if (element.nodeName == "FIELDSET" || element.nodeName == "INPUT" || element.nodeName == "TEXTAREA") {
            formElement = element;
            break;
        }
    }
    if (formElement.nodeName == "FIELDSET") {
        validateChoiceQuestion(formElement);
    } else {
        if (formElement.checkValidity()) {
            pages[currPage].style.display = "none";
            currPage++;
            pages[currPage].style.display = "flex";
        } else {
            formElement.reportValidity();
        }
    }
}

function validateChoiceQuestion(fieldsetElement) {
    let errorElement = fieldsetElement.parentNode.getElementsByClassName("error-message")[0];
    let isValid = true;
    let min = fieldsetElement.getAttribute("data-min");
    let max = fieldsetElement.getAttribute("data-max");
    // let required = fieldsetElement.getAttribute("data-required") ? true : false;
    let selected = Array.prototype.slice.call(fieldsetElement.getElementsByTagName("input"), 0).filter(function(input) {
            return input.checked;
        }).length;
    
    if (selected < min) {
        errorElement.innerHTML = "Select at least " + min;
        isValid = false;
    }
    if (selected > max) {
        errorElement.innerHTML += errorElement.innerHTML ? "\n" : "";
        errorElement.innerHTML += "Select at most " + max;
        isValid = false; 
    }
    if (isValid) {
        errorElement.innerHTML = "";
        errorElement.style.display = "none";
        pages[currPage].style.display = "none";
        currPage++;
        pages[currPage].style.display = "flex";
    } else {
        errorElement.style.display = "block";
    }
}