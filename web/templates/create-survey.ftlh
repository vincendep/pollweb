<form action="/pollweb/publish-survey" method="post">
    <label>Titolo: <input type="text" name="title"/></label></br>
    <label>Testo di apertura: <textarea name="openingText"></textarea></label></br>
    <label>Testo di chiusura: <textarea name="closingText"></textarea></label></br>
    <label>Tipo sondaggio:
        <input type="radio" name="type" value="reserved">Riservato</input>
        <input type="radio" name="type" value="public" checked>Pubblico</input>
    </label>
    </br>
    <input type="submit" name="create-survey" value="Pubblica sondaggio"/>
</form>
<label>Aggiungi domanda:
    <form method="post" action="">
        <select name="question-type">
            <option value="choice">Scelta</option>
            <option value="date">Data</option>
            <option value="number">Numero</option>
            <option value="short text">Testo breve</option>
            <option value="long text">Testo lungo</option>
        </select>
        <input type="submit" name="add-question" value="Aggiungi"/>
    </form>
</label>