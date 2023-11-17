<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html  lang="ru">
<head>
    <title>Регистрация</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/view/css/signupstyle.css">

</head>
<body>
<div class="container">
    <button class="close-button" onclick="window.location.href='/semestrovka/concert';">&#10005;</button>
    <h1>Регистрация</h1>
    <form method="post">
        <div class="form-group">
            <label for="surname">Фамилия:</label>
            <input type="text" id="surname" name="surname" pattern="^[А-Я][а-я]+$" required oninvalid="setCustomValidity('Введите фамилию с заглавной буквой и на русском языке')" oninput="setCustomValidity('')">
        </div>
        <div class="form-group">
            <label for="username">Имя:</label>
            <input type="text" id="username" name="username" pattern="^[А-Я][а-я]+$" required oninvalid="setCustomValidity('Введите имя с заглавной буквой и на русском языке')" oninput="setCustomValidity('')">

        </div>
        <div class="form-group">
            <label for="email">Почта:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" minlength="8" maxlength="16">
        </div>
        <button type="submit" >Зарегистрироваться</button>
        <span class="login-link"><a href="sign_in">Есть аккаунт?</a></span>
    </form>

</div>
</body>
</html>