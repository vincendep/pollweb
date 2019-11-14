<div class="container">
<form action="/pollweb/users" method="post">

<div class="row">
    <div class="col-25">
    <label for="email">Email: </label>
    </div>
    <div class="col-75">
    <input name="email" type="email" required /></br></br>
    </div>
</div>
<div class="row">
    <div class="col-25">
    <label for="password">Password: </label>
    </div>
    <div class="col-75">
    <input name="password" type="text" required /></br></br>
    </div>
</div>
<div class="row">
    <div class="col-25">
    <label for="name">Nome: </label>
    </div>
    <div class="col-75">
    <input name="name" type="text" required /></br></br>
    </div>
</div>
<div class="row">
    <div class="col-25">
    <label for="surname">Cognome: </label>
    </div>
    <div class="col-75">
    <input name="surname" type="text" required /></br></br>
    </div>
</div>
<div class="row">
    <div class="col-25">
    <label for="role">Ruolo: </label> </br>
    </div></br>
    <div class="col-75">
    <input type="radio" name="role" value="responsible" checked>Responsabile</input>&nbsp;
    <input type="radio" name="role" value="administrator">Amministratore</input>
    </div>
</div>
</br></br>
<div class="row">
    <input class="btn" type="submit" name="add" value="Aggiungi utente" />   
</div>
</form> 

</div></br>
<div  class="center">
<ul>
    <#list users as user>
        <li>${user.email} 
            <form style="display: inline;" action="/pollweb/users" method="post">
                <input type="hidden" name="n" value="#{user.id}">
                <button class="btn--small" type="submit" name="delete">
                    <i class="fas fa-trash-alt"></i>
                </button>
            </form>
        </li></br>
    </#list>
</ul>
</div>
