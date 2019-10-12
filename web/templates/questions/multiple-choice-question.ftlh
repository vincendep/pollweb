<#list question.options as option>
    <input type="checkbox" name="${question.code}" value="${option?index}">${option.text}</br>
</#list>
