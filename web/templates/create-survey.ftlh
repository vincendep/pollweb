<div class="container">
<form action="/pollweb/surveys" method="post">

<div class="row">
    <div class="col-25">
    <label>Titolo: </label>
    </div>
    <div class="col-75">
    <input type="text" name="title" required/>
    </div>
</div><br>
<div class="row">
    <div class="col-25">
    <label>Testo di apertura: </label>
    </div>
    <div class="col-75">
    <textarea name="openingText"></textarea>
    </div>
</div><br>
<div class="row">
    <div class="col-25">
    <label>Testo di chiusura:</label>
    </div>
    <div class="col-75">
    <textarea name="closingText"></textarea>
   
    </div>
</div><br>
<div class="row">
    <div class="col-25">
    <label>Tipo sondaggio:  </label>
    </div><br>
    <div class="col-75">
        <input type="radio" name="type" value="reserved"> &nbsp; Riservato &nbsp;
        <input type="radio" name="type" value="public" checked> &nbsp; Pubblico
    </div>
</div><br>

<div class="row">
    <div class="col-25"></div>
    <div class="col-75">
    <input type="submit" name="create" value="Crea sondaggio" class="btn"/>
    </div>
</div>
</form>
</div>