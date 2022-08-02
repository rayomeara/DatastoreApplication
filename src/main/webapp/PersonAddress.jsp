<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Datastore Application</title>
</head>
<body>
	<div align="center">
        <h1>Addresses for Person: ${person.firstName} ${person.lastName}</h1>
        <h2>
            <a href="newAddress?personId=<c:out value='${person.id}' />">Add New Address</a>
            &nbsp;&nbsp;&nbsp;
            <a href="listPersons">List All People</a>
             
        </h2>
    </div>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Addresses</h2></caption>
            <tr>
                <th>ID</th>
                <th>Street</th>
                <th>City</th>
                <th>State</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="address" items="${person.addresses}">
                <tr>
                    <td><c:out value="${address.id}" /></td>
                    <td><c:out value="${address.street}" /></td>
                    <td><c:out value="${address.city}" /></td>
                    <td><c:out value="${address.state}" /></td>
                    <td>
                        <a href="editAddress?id=<c:out value='${address.id}' />&personId=<c:out value='${person.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deleteAddress?id=<c:out value='${address.id}' />&personId=<c:out value='${person.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>