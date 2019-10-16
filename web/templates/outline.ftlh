<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Pollweb - Online Surveys</title>
    <link rel="stylesheet" href="/pollweb/style/normalize.css" type="text/css"/>
    <link rel="stylesheet" href="/pollweb/style/outline.css" type="text/css"/>
    <link rel="stylesheet" href="/pollweb/style/about.css" type="text/css"/>
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
                <li style="float: right;"><a href="/pollweb/account">Account</a></li>
            <#else>
                <li style="float: right;"><a href="/pollweb/login">Accedi</a></li>
            </#if>
        </ul>
    </nav>
</header>
<div class="content">
    <#include content_tpl>
</div>
<footer>
    <p>
        Copyright &copy; 2014 ${defaults.author}. Page compiled on ${compiled_on?datetime}
    </p>
</footer>
</body>
</html>
