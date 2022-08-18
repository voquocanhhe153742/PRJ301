<%-- 
    Document   : report
    Created on : Aug 16, 2022, 11:53:26 AM
    Author     : admin
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="5px">
            <tr>
                <td></td>
                <c:forEach items="${requestScope.dates}" var="d" >
                    <td 
                        <c:if test="${d.dow eq 1 or d.dow eq 7}">
                            style="background-color: pink" 
                        </c:if>
                        <c:if test="${d.dow ne 1 and d.dow ne 7}">
                            style="background-color: bisque" 
                        </c:if>
                        >
                        <fmt:formatDate pattern = "dd" 
                                        value = "${d.value}" /> <br/>
                        <fmt:formatDate pattern = "EE"
                                        value = "${d.value}" />
                    </td>
                </c:forEach>
                    <td>Giờ Làm</td>
                    <td>Ngày Làm</td>
                    <td>Nghỉ Phép </td>
                    <td>Nghỉ Không Phép </td>
                    <td>Lương </td>
            </tr>
            <c:forEach items="${requestScope.emps}" var="e">
                <tr>
                    <td>${e.name}</td>
                    <c:forEach items="${requestScope.dates}" var="d">
                        <td 
                            <c:if test="${d.dow eq 1 or d.dow eq 7}">
                                style="background-color: pink" 
                            </c:if>
                            >
                            <c:forEach items="${e.timesheets}" var="t">
                                <c:if test="${d.value eq t.cidate}">
                                    ${t.getWorkingHours()}
                                </c:if>
                            </c:forEach>
                        </td>
                    </c:forEach>
                    <td>${e.getWorkingHours()}</td>
                    <td>${e.getWorkingDays()}</td>
                    <td>2</td>
                    <td>5</td>
                    <td>2000$</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
