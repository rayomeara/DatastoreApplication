<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Datastore Application</title>
</head>
<body>
    <div align="center">
        <h1>Person Management</h1>
        <h2>
            <a href="newPerson">Add New Person</a>
            &nbsp;&nbsp;&nbsp;
            <a href="listPersons">List All People</a>
             
        </h2>
    </div>
    <div align="center">
        <c:if test="${person != null}">
            <form action="updatePerson" method="post">
        </c:if>
        <c:if test="${person == null}">
            <form action="insertPerson" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${person != null}">
                        Edit Person
                    </c:if>
                    <c:if test="${person == null}">
                        Add New Person
                    </c:if>
                </h2>
            </caption>
                <c:if test="${person != null}">
                    <input type="hidden" name="id" value="<c:out value='${person.id}' />" />
                </c:if>           
            <tr>
                <th>First Name: </th>
                <td>
                    <input type="text" name="firstName" size="45"
                            value="<c:out value='${person.firstName}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Last Name: </th>
                <td>
                    <input type="text" name="lastName" size="45"
                            value="<c:out value='${person.lastName}' />"
                    />
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>   
</body>
</html>