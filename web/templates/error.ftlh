<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Pollweb - Online Surveys</title>
    <link rel="stylesheet" href="/pollweb/style/normalize.css" type="text/css"/>
    <link rel="stylesheet" href="/pollweb/style/outline.css" type="text/css"/>
    <link rel="stylesheet" href="/pollweb/style/custom.css" type="text/css"/>
    <script src="https://kit.fontawesome.com/11a7f05f45.js" crossorigin="anonymous"></script>
</head>
<body class="boh">
    <header>
        <div class="title">
            <h1>Pollweb</h1>
            <h3>${page_title!"Error page"}</h3>
        </div>
        <nav>
            <ul class="topnav">
                <li><a href="/pollweb">Homepage</a></li>
                <li><a href="/pollweb/about">About</a></li>
                <#if logged_user??>
                    <li style="float: right;"><a href="/pollweb/account"><i class="far fa-user-circle"></i> Account</a></li>
                <#else>
                    <li style="float: right;"><a href="/pollweb/login">Accedi</a></li>
                </#if>
            </ul>
        </nav>
    </header> 
    <div class="title">
        <h1>Ooops! C'&egrave; stato un errore!</h1>
        <img src="/pollweb/images/error.jpg" alt="pollweb"/ height="120" width="120">
        <h3><b>${error!"Unknown error"}</b></h3>
        <span><a class="btn" href="/pollweb">Return to homepage</a></span>
    </div>

    <footer>
        <p>Copyright &copy; 2019 ${defaults.author}</p>
        <img src="/pollweb/images/univaq.png" alt="pollweb"/>
        <p>Page compiled on ${compiled_on?datetime}</p>
    </footer>
</body>
</html>