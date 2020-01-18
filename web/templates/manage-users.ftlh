<div class="manage-users">
    <h4 style="text-align: center;">Utenti registrati</h4>
    <table class="users-list">
        <thead>
            <td>Email</td>
            <td>Livello</td>
            <td></td>
        </thead>
        <tbody>
            <#list users as user>
            <tr>
                <td>${user.email}</td>
                <td><#if user.role == 'ADMINISTRATOR'>Amministratore <#else>Responsabile</#if></td>
                <td>
                    <form action="/pollweb/users" method="post">
                        <input type="hidden" name="n" value="#{user.id}">
                        <input type="hidden" name="delete">
                        <button onclick="deleteAlert(this.form)" class="delete-btn" type="button">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
<div class="add-user">
    <h4 class="add-user-title">Aggiungi un nuovo utente</h4>
    <form class="add-user-form" action="/pollweb/users" method="post">

        <div class="input-field">
            <label id="email">Email <input name="email" type="email" required /></label>
        </div>
        
        <div class="input-field">
            <label id="password">Password <input name="password" type="text" required /></label>
        </div>

        <div class="input-field">
            <label id="name">Nome <input name="name" type="text" required /></label>
        </div>
        
        <div class="input-field">
            <label id="surname">Cognome <input name="surname" type="text" required /></label>
        </div>
        
        <div style="text-align: center">
            <label id="role">Ruolo:</label>
            <label><input type="radio" name="role" value="responsible" checked>Responsabile</label>
            <label><input type="radio" name="role" value="administrator">Amministratore</label>
        </div>
        <div style="text-align: center;"><input class="btn" type="submit" name="add" value="Aggiungi utente" /></div>
    </form> 
</div>

<script type="text/javascript">
    function deleteAlert(form) {
        if (window.confirm("Sei sicuro di volere eliminare l'utente?")) {
            form.submit();
        } else {

        }
    }
</script>
