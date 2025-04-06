<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/01/28
  Time: 11:05 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <title>R.LOG</title>
    <script>

    </script>
</head>
<body>
    <h3>회원가입 페이지</h3>
    <form name = "signup"  method="post" action="/signup">
        <label>
            <p>이메일</p>
            <input type="text" name = "email" value =""/>
        </label>
        <label>
            <p>비밀번호</p>
            <input type="password" name = "password" value =""/>
        </label>
        <label>
            <p>사용자 이름</p>
            <input type="text" name = "userName" value =""/>
        </label>
        <label>
            <p>권한</p>
            <select name = "role">
                <option value = "ADMIN">관리자</option>
                <option value = "USER">사용자</option>
            </select>
        </label>
        <input type="submit" value="회원가입"/>
    </form>
</body>
</html>
