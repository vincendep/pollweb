<form action="/pollweb/users" method="post">
    <label for="email">Email: </label>
    <input name="email" type="email" required /></br>
    <label for="password">Password: </label>
    <input name="password" type="text" required /></br>
    <label for="name">Nome: </label>
    <input name="name" type="text" required /></br>
    <label for="surname">Cognome: </label>
    <input name="surname" type="text" required /></br>
    <label for="role">Ruolo: </label>
    <input type="radio" name="role" value="responsible" checked>Responsabile</input>
    <input type="radio" name="role" value="administrator">Amministratore</input></br>
    <input type="submit" name="add" value="Aggiungi utente" />
</form>
<ul>
    <#list users as user>
        <li>${user.email} 
            <form style="display: inline;" action="/pollweb/users" method="post">
                <input type="hidden" name="n" value="#{user.id}">
                <button type="submit" name="delete">
                    <i class="fas fa-trash-alt"></i>
                </button>
            </form>
        </li>
    </#list>
</ul>
