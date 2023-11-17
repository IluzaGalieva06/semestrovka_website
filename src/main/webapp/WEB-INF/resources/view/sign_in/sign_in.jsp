<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход</title>
    <style><%@include file="signInStyle.css"%></style>

</head>
<body>
<div class="container">
    <button class="close-button" onclick="window.location.href='/semestrovka/concert';">&#10005;</button>
    <h1>Вход</h1>
    <form method="post">
        <div class="form-group">
            <label for="email">Почта:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit">Войти</button>
        <span class="register-link"><a href="sign_up">Ещё нет аккаунта?</a></span>
    </form>
</div>
</body>
</html>

</html>