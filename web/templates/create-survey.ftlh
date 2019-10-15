<form action="/pollweb/surveys" method="post">
    <label>Titolo: <input type="text" name="title"/></label></br>
    <label>Testo di apertura: <textarea name="openingText"></textarea></label></br>
    <label>Testo di chiusura: <textarea name="closingText"></textarea></label></br>
    <label>Tipo sondaggio:
        <input type="radio" name="type" value="reserved">Riservato</input>
        <input type="radio" name="type" value="public" checked>Pubblico</input>
    </label>
    </br>
    <input type="submit" name="create" value="Crea sondaggio"/>
</form>