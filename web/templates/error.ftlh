<head>
        <title>Errore</title>
      <link rel="stylesheet" href="/pollweb/style/custom.css" type="text/css"/>
      <link rel="stylesheet" href="/pollweb/style/normalize.css" type="text/css"/>
      <link rel="stylesheet" href="/pollweb/style/outline.css" type="text/css"/>
    </head>
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
<body class="boh">
        <div class="title">
            <h1>Error</h1>
            <img src="/pollweb/images/error.jpg" alt="pollweb"/ height="120" width="120">
            <h2>The system encountered the following error while handling your request:</h2>
            <h3><b>${error!"Unknown error"}</b></h3>
            <span><a class="btn" href="/pollweb">Return to homepage</a></span>
        </div>
    </body>
<footer>
    <p>Copyright &copy; 2019 ${defaults.author}</p>
    <img src="/pollweb/images/univaq.png" alt="pollweb"/>
    <p>Page compiled on ${compiled_on?datetime}</p>
</footer>