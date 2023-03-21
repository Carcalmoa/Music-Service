<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <title>Music Information Service</title>
    <link rel='stylesheet' href='musicService.css'>
    <body>
        <h1>Music Information Service</h1><br>
        <h2>Query 1: Phase 2 (Country: ${bean12.country})</h2>
        <h3>Please, select an Album:</h3>
        <ol>
            <c:forEach items="${bean12.albums}" var="album">
                <li><a href='?p=${bean12.password}&pphase=13&pcountry=${bean12.country}&paid=${album.aid}'><b>Album: </b><c:out value="'${album.name}'"></c:out></a><b> --- Year: </b><c:out value="'${album.year}'"></c:out><b> --- Performer: </b><c:out value="'${album.performer}'"></c:out><b> --- Review: </b><c:out value="'${album.review}'"></c:out></li>
            </c:forEach>        
        </ol><br>
        <button id='home_button' onclick="location.href='?p=${bean12.password}&pphase=01'">Home</button>
        <button type='button' id='back_button' onclick="location.href='?p=${bean12.password}&pphase=11'">Back</button>
        <hr>
        &copy; Carlos Calvo Moa (2022-2023)
    </body>
</html>
