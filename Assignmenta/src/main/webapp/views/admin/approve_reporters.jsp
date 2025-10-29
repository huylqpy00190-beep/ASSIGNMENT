<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Duyệt phóng viên</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>

<div class="container">
  <div class="card">
    <h2>Duyệt phóng viên</h2>

    <table style="width:100%;border-collapse:collapse;margin-top:12px;">
      <thead><tr><th>Họ tên</th><th>Email</th><th>Ngày yêu cầu</th><th>Thao tác</th></tr></thead>
      <tbody>
        <c:forEach var="r" items="${requests}">
          <tr>
            <td>${r.fullname}</td>
            <td>${r.email}</td>
            <td><fmt:formatDate value="${r.requestedAt}" pattern="dd/MM/yyyy HH:mm"/></td>
            <td>
              <form action="${pageContext.request.contextPath}/quan-ly-nguoi-dung/duyet-phong-vien" method="post">
                <input type="hidden" name="email" value="${r.email}">
                <button type="submit" class="button">Duyệt</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
