<%@ page import="com.infoshareacademy.model.User" %>
<%@ page import="com.infoshareacademy.repository.UserRepository" %><%--
  Created by IntelliJ IDEA.
  User: kacper-kwiatkowski
  Date: 16.09.2020
  Time: 09:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>

<%
    UserRepository userRepository = new UserRepository();
    userRepository.fillUsersList();
%>
<div class="container-fluid">
    <div class="container-fluid" style="overflow: auto">
        <br>
        <h3>
            Search for employee:
        </h3>
        <input class="form-control" id="myInput" type="text" placeholder="Type here..."><br>
        <div>
            <table>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email address</th>
                    <th>Remaining days off</th>
                </tr>
                <% for (User user : userRepository.getUsersList()) { %>
                <tr>
                    <td><%=user.getId() %></td>
                    <td><%=user.getFirstName() %></td>
                    <td><%=user.getLastName() %></td>
                    <td><%=user.getEmail() %></td>
                    <td><%=user.getDaysOffLeft() %></td>
                </tr>
                <% } %>
            </table>
        </div>

    </div>
</div>
</body>
