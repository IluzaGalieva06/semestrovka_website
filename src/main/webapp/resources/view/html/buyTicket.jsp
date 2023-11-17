<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Покупка билетов</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%@ include file="menu.jsp" %>
<div class="container mt-5">


  <h2>Оплата билета</h2>
  <div class="form-group">
    <label >Имя концерта: ${concertName}</label>

  </div>
  <form action="processPurchase" method="post">
    <div class="form-group">
      <label for="cardNumber">Номер карты:</label>
      <input type="text" class="form-control" id="cardNumber" name="cardNumber" placeholder="Введите номер карты"
             pattern="[0-9]{16}" title="Номер карты должен состоять из 16 цифр" required>
    </div>
    <div class="form-group">
      <label for="cardCode">Код карты:</label>
      <input type="text" class="form-control" id="cardCode" name="cardCode" placeholder="Введите код карты"
             pattern="[0-9]{3}" title="Код карты должен состоять из 3 цифр" required>
    </div>
    <div class="form-group">
      <label>Цена билета:${concertPrice}</label>

    </div>
    <input type="hidden" id="concertPrice" name="concertPrice" value="${concertPrice}">
    <input type="hidden" id="concertName" name="concertName" value="${concertName}">

    <button type="submit" class="btn btn-primary">Оплатить</button>
  </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.9/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>