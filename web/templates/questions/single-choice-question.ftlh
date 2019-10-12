<#list question.options as option>
    <input type="radio" name="${question.code}" value="${option?index}">${option.text}</br>
</#list>
