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
    <h1 style="text-align: center">Bảng chấm công</h1>
    <body>
        <form style="margin-bottom: 30px" action="report" method="post">
            <input type="text" name="name" value="${name}" placeholder="Search by name">
            <input type="submit" value="Search">
        </form>
        <table border="5px">
            <tr>
                <td>Name</td>
                <c:forEach items="${requestScope.dates}" var="d" >
                    <td 
                        <c:if test="${d.dow eq 1 or d.dow eq 7}">
                            style="background-color: yellow" 
                        </c:if>
                        <c:if test="${d.dow ne 1 and d.dow ne 7}">
                            style="background-color: bisque" 
                        </c:if>
                        >
                        <fmt:formatDate pattern = "dd" 
                                        value = "${d.value}" /> /08<br/>
                        <fmt:formatDate pattern = "EE"
                                        value = "${d.value}" />
                    </td>
                </c:forEach>
                <td>Hours</td>
                <td>Days</td>
                <td>Paid leave </td>
                <td>Unpaid leave </td>
                <td>Holiday </td>
                <td>Total salary </td>
            </tr>
            <c:forEach items="${requestScope.emps}" var="e">
                <tr>
                    <td>${e.name}</td>
                    <c:forEach items="${requestScope.dates}" var="d">
                        <td 
                            <c:if test="${d.dow eq 1 or d.dow eq 7}">
                                style="background-color: yellow" 
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
                    <td>${e.paidleave}</td>
                    <td>${e.unpaidleave}</td>
                    <td>${e.holiday}</td>
                    <td>${e.salary}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
