<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <title>Music Information Service</title>
    <link rel='stylesheet' href='musicService.css'>
    <body>
        <h1>Music Information Service</h1><br>
        <h2>Query 1: Phase 3 (Country: ${bean13.country} , Album: ${bean13.aid})</h2>
        <h3>This is the query result:</h3>
        <ol>
            <c:forEach items="${bean13.songs}" var="song">
                <li><b>Tittle: </b><c:out value="'${song.title}'"></c:out><b> --- Language: </b><c:out value="'${song.language}'"></c:out><b> --- Genres: </b><c:out value="'${song.genre}'"></c:out><b> --- Composer: </b><c:out value="'${song.composer}'"></c:out></li>
            </c:forEach>
        </ol><br>
        <button id='home_button' onclick="location.href='?p=${bean13.password}&pphase=01'">Home</button>
        <button type='button' id='back_button' onclick="location.href='?p=${bean13.password}&pphase=12&pcountry=${bean13.country}'">Back</button>
        <hr>
        &copy; Carlos Calvo Moa (2022-2023)
    </body>
</html>
