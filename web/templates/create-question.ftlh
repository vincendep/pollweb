<form class="auth-form" action="/pollweb/questions" method="get">
    <input type="hidden" name="survey" value="#{survey}"/>
    <input type="hidden" name="type" value="${type}"/>
    <div class="form-field">
        <label>Titolo:</label>
        <input type="text" name="text"/>
    </div>
    <div class="form-field">
        <label>Codice:</label>
        <input type="text" name="code"/>
    </div>
    <div class="form-field">
        <label>Nota:</label>
        <textarea name="note"></textarea>
    </div>
    <div class="title">
    <input type="checkbox" name="mandatory" checked>Obbligatoria</input></br>
    </div>
    
    <#if type == "short text">
        <div class="form-field">
            <label>Minimo numero di caratteri:</label>
            <input type="number" name="min-num-char"/><br>
            <label>Massimo numero di caratteri:</label>
            <input type="number" name="max-num-char"/><br>
            <label>Pattern: </label>
            <input type="text" name="pattern"/><br>
        </div>
    <#elseif type == "long text">
        <div class="form-field">
            <label>Minimo numero di caratteri:</label>
            <input type="number" name="min-num-char"/></br>
            <label>Massimo numero di caratteri:</label>
            <input type="number" name="max-num-char"/></br>
        </div>
    <#elseif type == "date">
        <div class="form-field">
            <label>Data minima:</label>
            <input type="date" name="min-date"/></br>
            <label>Data massima:</label>
            <input type="date" name="max-date"/></br>
        </div>
    <#elseif type == "number">
        <div class="form-field">   
            <label>Minimo numero:</label>
            <input type="number" name="min-num"/></br>
            <label>Massimo numero:</label>
            <input type="number" name="max-num"/></br>
        </div>
    <#elseif type == "choice">
        <div class="form-field">   
            <label>Minimo numero di scelte:</label>
            <input type="number" name="min-num-choices"/><br>
            <label>Massimo numero di scelte:</label>
            <input type="number" name="max-num-choices"/><br>
            <label>Scelte: </label>
            <textarea name="choices">Inserire le scelte separate da ";"</textarea>
        </div>
    </#if>
    <div class="title">
    <input type="submit" class="btn" name="create" value="Conferma"/>
</div>
</form>
