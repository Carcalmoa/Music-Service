<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <title>Music Information Service</title>
    <link rel='stylesheet' href='musicService.css'>
    <body>
        <h1>Music Information Service</h1><br>
        <h3>Files with errors: ${bean02.errorsLink.size()}</h3>
        <ul>
            <c:forEach var="errorsLink" items="${bean02.errorsLink}">
                <li><c:out value="${errorsLink}"></c:out></li>
            </c:forEach>
        </ul>
        <h3>Files with fatal errors: ${bean02.fatalErrorsLink.size()}</h3>
        <ul>
            <c:forEach var="fatalErrorsLink" items="${bean02.fatalErrorsLink}">
                <li><c:out value="${fatalErrorsLink}"></c:out></li>
            </c:forEach>
        </ul>
        <button id='back_button' onclick="location.href='?p=${bean02.password}&pphase=01'">Back</button>
        <hr>
        &copy; Carlos Calvo Moa (2022-2023)
    </body>
</html>