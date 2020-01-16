<div class="container">
<form action="/pollweb/users" method="post">

<div class="row">
    <div class="col-25">
    <label id="email">Email: </label>
    </div>
    <div class="col-75">
    <input name="email" type="email" required /><br><br>
    </div>
</div>
<div class="row">
    <div class="col-25">
    <label id="password">Password: </label>
    </div>
    <div class="col-75">
    <input name="password" type="text" required /><br><br>
    </div>
</div>
<div class="row">
    <div class="col-25">
    <label id="name">Nome: </label>
    </div>
    <div class="col-75">
    <input name="name" type="text" required /><br><br>
    </div>
</div>
<div class="row">
    <div class="col-25">
    <label id="surname">Cognome: </label>
    </div>
    <div class="col-75">
    <input name="surname" type="text" required /><br><br>
    </div>
</div>
<div class="row">
    <div class="col-25">
    <label id="role">Ruolo: </label> <br>
    </div><br>
    <div class="col-75">
    <input type="radio" name="role" value="responsible" checked>Responsabile &nbsp;
    <input type="radio" name="role" value="administrator">Amministratore
    </div>
</div>
<br><br>
<div class="row">
<div class="col-25"></div>
<div class="col-75">
    <input class="btn" type="submit" name="add" value="Aggiungi utente" />   
</div>
</div>
</form> 

</div><br>
<div  class="center">
<ul>
    <#list users as user>
        <li>${user.email} 
            <form style="display: inline;" action="/pollweb/users" method="post">
                <input type="hidden" name="n" value="#{user.id}">
                <input type="hidden" name="delete">
                <button onclick="deleteAlert(this.form)" class="delete-btn" type="button">
                    <i class="fas fa-trash-alt"></i>
                </button>
            </form>
        </li>
    </#list>
</ul>
</div>
<script type="text/javascript">
    function deleteAlert(form) {
        if (window.confirm("Sei sicuro di volere eliminare l'utente?")) {
            form.submit();
        } else {

        }
    }
</script>
