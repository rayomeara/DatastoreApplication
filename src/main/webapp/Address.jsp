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
            <a href="newAddress">Add New Address</a>
            &nbsp;&nbsp;&nbsp;
            <a href="listAddresses">List All Addresses</a>
             
        </h2>
    </div>
    <div align="center">
        <c:if test="${address != null}">
            <form action="updateAddress" method="post">
        </c:if>
        <c:if test="${address == null}">
            <form action="insertAddress" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${address != null}">
                        Edit Address
                    </c:if>
                    <c:if test="${address == null}">
                        Add New Address
                    </c:if>
                </h2>
            </caption>
                <c:if test="${address != null}">
                    <input type="hidden" name="addressId" value="<c:out value='${address.id}' />" />
                </c:if>           
                <input type="hidden" name="personId" value="<c:out value='${personId}' />" />
            <tr>
                <th>Street: </th>
                <td>
                    <input type="text" name="street" size="45"
                            value="<c:out value='${address.street}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>City: </th>
                <td>
                    <input type="text" name="city" size="45"
                            value="<c:out value='${address.city}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>State: </th>
                <td>
                    <input type="text" name="state" size="45"
                            value="<c:out value='${address.state}' />"
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