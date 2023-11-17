<%@ page import="kpfu.itis.dto.UserDto" %>
<%@ page import="kpfu.itis.models.UserRole" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Подробная информация</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/view/css/concertDetailsStyle.css">

</head>
<body>
<%@ include file="menu.jsp" %>
<%UserDto userDto = (UserDto) request.getSession().getAttribute("user");%>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <h2 class="text-center">Подробная информация о концерте</h2>
            <c:if test="${not empty concertDetails}">
                <div class="card">
                    <div class="image-container">
                        <img src="${pageContext.request.contextPath}/files/${concertDetails.posterId}" class="card-img-top" alt="Постер концерта" width="300" height="400">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Название концерта: ${concertDetails.name}</h5>
                        <p class="card-text"><strong>Исполнители:</strong>
                            <c:forEach var="singerName" items="${singerNames}" varStatus="loopStatus">
                                ${singerName}<c:if test="${!loopStatus.last}">, </c:if>
                            </c:forEach>
                        </p>


                        <p class="card-text"><strong>Место:</strong> ${concertDetails.location}</p>
                        <p class="card-text"><strong>Дата:</strong> ${concertDetails.date}</p>
                        <p class="card-text"><strong>Описание:</strong> ${concertDetails.description}</p>
                        <p class="card-text"><strong>Цена:</strong> ${concertDetails.price}</p>
                        <div class="button-container">
                            <button type="button" class="btn btn-primary" onclick="buyTicket(${concertDetails.price}, '${concertDetails.name}')">Купить билет</button>
                            <%
                                if (userDto != null && userDto.getRole() == UserRole.ADMIN) {
                            %>

                            <button type="button" class="btn btn-danger" onclick="deleteConcert(${concertDetails.id})">Удалить концерт</button>
                            <button type="button" class="btn btn-warning" onclick="editConcert(${concertDetails.id})">Редактировать концерт</button>

                            <%
                                }
                            %>
                        </div>
                    </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty concertDetails}">
                <p class="text-center">No details available for this concert.</p>
            </c:if>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<%

    boolean isLogIn = (session.getAttribute("user") != null);


%>
</body>
<script>
    function buyTicket(price,name) {
        var isLoggedIn = <%= isLogIn %>;

        if (isLoggedIn) {
            window.location.href = "/semestrovka/buy-ticket?price=" + price + "&name=" + encodeURIComponent(name);
        } else {
            window.location.href = "/semestrovka/sign_up";
        }
    }
</script>
<script>
    function deleteConcert(concertId) {
        window.location.href = "/semestrovka/delete?concertId=" + concertId;
    }
</script>
<script>
    function editConcert(concertId) {
        window.location.href = "/semestrovka/update-concert?concertId=" + concertId;
    }
</script>


</html>