<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>用户注册</h1>
    <form action="${pageContext.request.contextPath}/user/register" method="post">
        用户名：<input type="text" name="username" id="username"><br>
        密码：<input type="text" name="password" id="password">
        <div style="margin-top: 10px">
            <input type="submit" value="立即注册" id="register">
            <input type="button" value="返回登录" id="retBtn">
        </div>
    </form>
</body>
</html>