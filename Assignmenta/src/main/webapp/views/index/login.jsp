<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container" style="max-width:480px;margin-top:40px">
  <div class="card">
    <h2>Đăng nhập</h2>
    <form action="${pageContext.request.contextPath}/dang-nhap" method="post">
      <div class="form-row">
        <label for="id">Tài khoản</label>
        <input id="id" name="id" type="text" required>
      </div>
      <div class="form-row">
        <label for="password">Mật khẩu</label>
        <input id="password" name="password" type="password" required>
      </div>
      <button class="button" type="submit">Đăng nhập</button>
    </form>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
