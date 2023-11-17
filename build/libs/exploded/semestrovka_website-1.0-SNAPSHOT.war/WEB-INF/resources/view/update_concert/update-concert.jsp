<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Редактирование концерта</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<%@ include file="../menu/menu.jsp" %>

<div class="container mt-5">
  <h2>Редактирование концерта</h2>
  <form action="update-concert" method="post">
    <div class="form-group">
      <label for="concertId">Идентификатор концерта:</label>
      <input type="hidden" id="concertId" name="concertId" value="<%= request.getParameter("concertId") %>">
    </div>
    <div class="form-group">
      <label for="concertName">Название концерта:</label>
      <input type="text" class="form-control" id="concertName" name="concertName" placeholder="Введите название концерта">
    </div>
    <div class="form-group">
      <label for="concertDate">Дата концерта:</label>
      <input type="date" class="form-control" id="concertDate" name="concertDate">
    </div>
    <button type="submit" class="btn btn-primary">Сохранить</button>
  </form>

</div>

<!-- Подключение Bootstrap JS (необходим для работы некоторых компонентов) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
