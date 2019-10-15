<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Pollweb - Online Surveys</title>
    <#--<link rel="stylesheet" href="/pollweb/style/normalize.css" type="text/css"/>
    <link rel="stylesheet" href="/pollweb/style/grid.css" type="text/css"/>
    <link rel="stylesheet" href="/pollweb/style/default.css" type="text/css"/>-->
    <link rel="stylesheet" href="/pollweb/style/custom.css" type="text/css"/>
</head>
<body>
<header>
    <h1>Pollweb</h1>
    <h3>${page_title!"Untitled page"}</h3>
    <ul>
        <li><a href="/pollweb">Homepage</a></li>
        <li><a href="/pollweb/about">About page</a></li>
        <#if logged_user??>
            <li><a href="/pollweb/account">
                    <div>Ciao, ${logged_user.name}</div>
                    <div>Account</div>
                </a></li>
            <li>
                <form action="/pollweb/auth"><input type="submit" name="logout" value="Esci"/></form>
            </li>
        <#else>
            <li><a href="/pollweb/login">Accedi</a></li>
        </#if>
    </ul>
    <hr/>
</header>
<div class="content">
    <#include content_tpl>
</div>
<footer>
    <hr/>
    Copyright &copy; 2014 ${defaults.author}. Page compiled on ${compiled_on?datetime}
</footer>
</body>
</html>
