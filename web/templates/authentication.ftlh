<form action="/pollweb/auth" method="post">
<div class="container">
    <input type="hidden" name="survey" value="#{survey}"/>
    <div class="row">
        <div class="col-25">
        <label id="email">Email: </label>
        </div>
        <div class="col-75">
        <label> 
        <input type="email" name="email" required="required"/>
        </label>
        </div>
    </div>
    <div class="row">
        <div class="col-25">
        <label id="password">Password: </label>
        </div>
        <div class="col-75">
        <label>
        <input type="password" name="password" required="required" />
        </label>
        </div>

    </div>
    <div class="row">
        <div class="col-25"></div>
        <div class="col-75">
        <input type="submit" class="btn" name="authenticate" value="Autenticati"/>
        </div>
    </div>
</div>
</form>
