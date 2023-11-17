<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Добавление концертов</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="menu.jsp" %>
<div class="container mt-5">
  <h2 class="text-center">Добавление концертов</h2>

  <form action="add-concert" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="name">Название концерта:</label>
      <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="form-group">
      <label for="location">Место проведения:</label>
      <input type="text" class="form-control" id="location" name="location" required>
    </div>
    <div class="form-group">
      <label for="singers">Исполнители:</label>
      <input type="text" class="form-control" id="singers" name="singers" required>
      <small id="singersHelp" class="form-text text-muted">Введите имена исполнителей через запятую.</small>
    </div>
    <div class="form-group">
      <label for="date">Дата концерта:</label>
      <input type="date" class="form-control" id="date" name="date" required>
    </div>
    <div class="form-group">
      <label for="description">Описание:</label>
      <textarea class="form-control" id="description" name="description" rows="4" required></textarea>
    </div>

    <div class="form-group">
      <label for="price">Цена билета:</label>
      <input type="number" class="form-control" id="price" name="price" required>
    </div>


    <div class="form-group mt-4">
      <label for="poster">Загрузить фото:</label>
      <input type="file" class="form-control-file" id="poster" name="poster" accept="image/*" required>
    </div>
    <button type="submit" class="btn btn-primary">Добавить концерт</button>
  </form>


</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>