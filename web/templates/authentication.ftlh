<form action="/pollweb/auth" method="post">
    <input type="hidden" name="survey" value="#{survey}"/>
    <label>Email: <input type="email" name="email" required="required" ></label>
    <label>Password: <input type="password" name="password" required="required" ></label>
    <input type="submit" class="btn" name="authenticate" value="Autenticati">
</form>
