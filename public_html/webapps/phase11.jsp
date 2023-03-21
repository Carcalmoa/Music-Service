<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <title>Music Information Service</title>
    <link rel='stylesheet' href='musicService.css'>
    <body>
        <h1>Music Information Service</h1><br>
        <h2>Query 1: Phase 1</h2>
        <h3>Please, select a Country:</h3>
        <ol>
            <c:forEach items="${bean11.countries}" var="country">
                <li><a href='?p=${bean11.password}&pphase=12&pcountry=${country}'><c:out value="${country}"></c:out></a></li>
            </c:forEach>
        </ol><br>
        <button id='home_button' onclick="location.href='?p=${bean11.password}&pphase=01'">Home</button>
        <hr>
        &copy; Carlos Calvo Moa (2022-2023)
    </body>
</html>