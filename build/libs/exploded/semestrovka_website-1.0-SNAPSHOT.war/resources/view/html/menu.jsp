<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Menu</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/view/css/menu.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark ">
    <div class="container-fluid">
        <a class="navbar-brand" href="concert">Афиша концертов</a>
        <form class="w-50 d-flex" method="post" action="search">
            <input name="searchTerm" class="form-control me-2 flex-grow-1" type="search" placeholder="Поиск" aria-label="Поиск">
            <button class="btn btn-outline-success" type="submit">Поиск</button>

        </form>
        <button type="button" class="btn btn-outline-light position-relative ms-2">
            <i class="bi bi-person-circle"></i>
            Профиль
        </button>
    </div>

</nav>

<%

    boolean isLoggedIn = (session.getAttribute("user") != null);


%>

</body>
<script>
    $(document).ready(function() {
        var isLoggedIn = <%= isLoggedIn %>


            $("button.btn.btn-outline-light").click(function() {
                if (isLoggedIn) {
                    window.location.href = "/semestrovka/profile";
                } else {
                    window.location.href = "/semestrovka/sign_up";
                }
            });
    });

</script>
</html>
