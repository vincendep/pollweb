<form action="/pollweb/questions" method="get">
    
    <input type="hidden" name="survey" value="#{survey}"/>
    <input type="hidden" name="type" value="${type}"/>

    <label>Titolo: <input type="text" name="text"/></label></br>
    <label>Codice: <input type="text" name="code"/></label></br>
    <label>Nota: <textarea name="note"></textarea></label></br>
    <input type="checkbox" name="mandatory" checked>Obbligatoria</input></br>
    <#if type == "short text">
        <label>Minimo numero di caratteri: <input type="number" name="min-num-char"/></label></br>
        <label>Massimo numero di caratteri: <input type="number" name="max-num-char"/></label></br>
        <label>Pattern: <input type="text" name="pattern"/></label></br>
    <#elseif type == "long text">
        <label>Minimo numero di caratteri: <input type="number" name="min-num-char"/></label></br>
        <label>Massimo numero di caratteri: <input type="number" name="max-num-char"/></label></br>
    <#elseif type == "date">
        <label>Data minima: <input type="date" name="min-date"/></label></br>
        <label>Data massima: <input type="date" name="max-date"/></label></br>
    <#elseif type == "number">
        <label>Minimo numero: <input type="number" name="min-num"/></label></br>
        <label>Massimo numero: <input type="number" name="max-num"/></label></br>
    <#elseif type == "choice">
        <label>Minimo numero di scelte: <input type="number" name="min-num-choices"/></label></br>
        <label>Massimo numero di scelte: <input type="number" name="max-num-choices"/></label></br>
        <label>Scelte: 
            <textarea name="choices">Inserire le scelte separate da ";"</textarea>
        </label>
    </#if>
    <input type="submit" name="create" value="Conferma"/>
</form>
