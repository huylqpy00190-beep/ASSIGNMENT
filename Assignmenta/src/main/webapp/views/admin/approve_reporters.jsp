<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Duyệt phóng viên</title>
<link rel="stylesheet" href="../css/style.css">
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
            <td>${r.requestedAt}</td>
            <td>
              <form action="<%= request.getContextPath() %>/quan-ly-nguoi-dung/duyet-phong-vien" method="post">
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
