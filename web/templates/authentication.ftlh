<form action="/pollweb/participants" method="post">
    <input type="hidden" name="survey" value="#{survey}"/>
    <div class="authentication">
        <label id="email">Email: </label>
        <label for="email"/> &nbsp &nbsp &nbsp
        <input type="email" name="email" required/>
    </div>
    <div class="authentication">
        <label id="password">Password: </label>
        <label for="password"/>
        <input type="password" name="password" required />
    </div>
    <div class="authentication">
        <input type="submit" class="btn" name="authenticate" value="Autenticati"/>
    </div>
</form>
