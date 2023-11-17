<%@ page import="kpfu.itis.dto.UserDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Профиль пользователя</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/view/css/profileStyle.css">

</head>
<body>
<%@ include file="menu.jsp" %>


<div class="container">
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" id="profile-tab" data-toggle="tab" href="#profile">Профиль</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="orders-tab" data-toggle="tab" href="#orders">Заказы</a>
        </li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane fade show active" id="profile">
            <h2>Профиль</h2>
            <%UserDto userDto = (UserDto) request.getSession().getAttribute("user");%>

            <c:choose>
                <c:when test="${not empty user.avatarId}">


                    <img class="user-avatar" alt="IMAGE"
                         src="${pageContext.request.contextPath}/files/${user.avatarId}">
                </c:when>
                <c:otherwise>
                    <img class="user-avatar" alt="IMAGE"
                         src="${pageContext.request.contextPath}/resources/view/no-avatar.png"/>
                </c:otherwise>
            </c:choose>
            <form action="update-avatar" method="post" enctype="multipart/form-data">
                <input type="file" name="file">
                <input type="submit" value="Изменить аватар">
            </form>


            <p><%="Фамилия: " +  userDto.getSurname()%></p>
            <p><%="Имя: " + userDto.getUsername()%></p>
            <p><%="Почта: " + userDto.getEmail()%></p>

            <div class="button-container">
                <button class="btn btn-primary" onclick="window.location.href='/semestrovka/sign_out';">Выход</button>
                <%
                    String role = ""+userDto.getRole();
                    if("ADMIN".equals(role)) {
                %>

                    <button class="btn btn-danger" onclick="window.location.href='/semestrovka/add-concert';">Админ-панель</button>
                <%
                    }
                %>
            </div>





        </div>

        <div class="tab-pane fade" id="orders">
            <h2>Заказы</h2>
            <div class="tickets-container">
                <c:choose>
                    <c:when test="${not empty ticketForms}">
                        <c:forEach var="ticket" items="${ticketForms}">
                            <div class="ticket">
                                <p><strong>Билет:</strong> ${ticket.id}</p>
                                <p><strong>Название концерта:</strong> ${ticket.concertName}</p>
                                <p><strong>Цена:</strong> ${ticket.price}</p>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>Нет доступных билетов.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>

</html>