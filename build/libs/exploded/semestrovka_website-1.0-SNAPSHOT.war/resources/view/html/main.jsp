<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Афиша концертов</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/view/css/mainStyle.css">

</head>
<body>
<%@ include file="menu.jsp" %>
<div class="container">
<div class="row justify-content-center">
    <div class="col-md-8">
        <h2 class="text-center">Концерты</h2>
        <c:choose>
            <c:when test="${not empty concertsForJsp}">
                <div class="row">

                    <c:forEach var="concert" items="${concertsForJsp}">
                        <div class="col-md-4">
                            <div class="card concert-item">


                                <div class="card-body">
                                        <c:choose>
                                        <c:when test="${not empty concert.posterId}">

                                            <img alt="IMAGE" src="${pageContext.request.contextPath}/files/${concert.posterId}" class="card-img-top">

                                        </c:when>
                                        <c:otherwise>
                                            <img class="card-img-top" alt="IMAGE"
                                                 src="${pageContext.request.contextPath}/resources/view/no-avatar.png"/>
                                        </c:otherwise>
                                        </c:choose>
                                    <a href="${pageContext.request.contextPath}/concertDetails?id=${concert.id}">
                                        <h3 class="card-title">${concert.name}</h3>
                                    </a>


                                    <p class="card-text"><strong>Исполнитель/и:</strong>
                                        <c:forEach var="singerName" items="${concertSingerMap[concert.id]}" varStatus="loopStatus">
                                            ${singerName}<c:if test="${!loopStatus.last}">, </c:if>
                                        </c:forEach>
                                    </p>

                                    <p class="card-text"><strong>Место:</strong> ${concert.location}</p>
                                        <p class="card-text"><strong>Дата:</strong> ${concert.date}</p>
                                        <p class="card-text"><strong>Описание:</strong> ${concert.description}</p>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </c:when>
            <c:otherwise>
                <p class="text-center">Нет концертов</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>