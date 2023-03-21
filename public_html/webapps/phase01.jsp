<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<html>
    <title>Music Information Service</title>
    <link rel='stylesheet' href='musicService.css'>
    <body>
        <h1>Music Information Service</h1><br>
        <h2>Welcome to this server</h2>
        <h3>Please, select a query:</h3>
        <ul>
            <li><a href='?p=${bean01.password}&pphase=02'> Show error files</a></li>
            <li><a href='?p=${bean01.password}&pphase=11'> Query 1: Pop songs of an Album of a Country</a></li>
        </ul><br>
        <hr>
        &copy; Carlos Calvo Moa (2022-2023)
    </body>
</html>