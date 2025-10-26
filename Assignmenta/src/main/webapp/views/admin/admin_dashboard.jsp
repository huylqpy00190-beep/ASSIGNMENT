<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
  <div class="card">
    <h2>Trang quản trị</h2>
    <div style="display:flex;gap:12px;flex-wrap:wrap;margin-top:12px">
      <a class="button" href="admin_news.jsp">Quản lý tin</a>
      <a class="button" href="admin_categories.jsp">Quản lý loại tin</a>
      <a class="button" href="admin_users.jsp">Quản lý người dùng</a>
      <a class="button" href="admin_newsletters.jsp">Quản lý newsletter</a>
    </div>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>