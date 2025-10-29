<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký nhận bản tin</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
  <div class="card">
    <h2>Đăng ký nhận bản tin</h2>
    <p>Nhập email để nhận thông báo khi có bản tin mới.</p>
    <form action="${pageContext.request.contextPath}/newsletter/subscribe" method="post">
      <div class="form-row">
        <label for="email">Email</label>
        <input id="email" name="email" type="email" placeholder="you@example.com" required>
      </div>
      <button class="button" type="submit">Đăng ký</button>
    </form>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
