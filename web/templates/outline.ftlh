<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Pollweb - Online Surveys</title>
    <link rel="stylesheet" href="/pollweb/style/normalize.css" type="text/css"/>
    <link rel="stylesheet" href="/pollweb/style/outline.css" type="text/css"/>
    <link rel="stylesheet" href="/pollweb/style/custom.css" type="text/css"/>
    <script src="https://kit.fontawesome.com/11a7f05f45.js" crossorigin="anonymous"></script>
</head>
<body>
<header>
    <div class="title">
        <h1>Pollweb</h1>
        <h3>${page_title!"Untitled page"}</h3>
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
<main>
    <#include content_tpl>
</main>
<footer>
    <p>Copyright &copy; 2019 ${defaults.author}</p>
    <img src="/pollweb/images/univaq.png"/>
    <p>Page compiled on ${compiled_on?datetime}</p>
</footer>
</body>
</html>
