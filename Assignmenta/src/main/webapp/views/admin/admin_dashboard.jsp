<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang quản trị</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
  <div class="card">
    <h2>Trang quản trị</h2>
    <div style="display:flex;gap:12px;flex-wrap:wrap;margin-top:12px">
      <a class="button" href="${pageContext.request.contextPath}/quan-ly-tin">Quản lý tin</a>
      <a class="button" href="${pageContext.request.contextPath}/quan-ly-loai-tin">Quản lý loại tin</a>
      <a class="button" href="${pageContext.request.contextPath}/quan-ly-nguoi-dung">Quản lý người dùng</a>
      <a class="button" href="${pageContext.request.contextPath}/admin/newsletters">Quản lý newsletter</a>
    </div>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
