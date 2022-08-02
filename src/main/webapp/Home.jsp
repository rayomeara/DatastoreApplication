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
        <h1>Person Management</h1>
        <h2>
            <a href="newPerson">Add New Person</a>
            &nbsp;&nbsp;&nbsp;
            <a href="listPersons">List All People</a>
             
        </h2>
    </div>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of People</h2></caption>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="person" items="${peopleList}">
                <tr>
                    <td><c:out value="${person.id}" /></td>
                    <td><c:out value="${person.firstName}" /></td>
                    <td><c:out value="${person.lastName}" /></td>
                    <td>
                    	
                        <a href="editPerson?id=<c:out value='${person.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="viewAddresses?id=<c:out value='${person.id}' />">View Address List</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="deletePerson?id=<c:out value='${person.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>