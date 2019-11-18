<form action="/pollweb/participants" method="post">
    <input type="hidden" name="survey" value="#{survey}"/>
    <label for="email"/>
    <input type="email" name="email" required/>
    <label for="password"/>
    <input type="password" name="password" required />
    <input type="submit" name="authenticate" value="Autenticati"/>
</form>
