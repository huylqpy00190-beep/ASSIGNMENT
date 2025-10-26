<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý người dùng</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>

<div class="container">
  <div class="card">
    <h2>Quản lý người dùng</h2>

    <!-- Form thêm người dùng -->
    <form action="<%= request.getContextPath() %>/quan-ly-nguoi-dung/them" method="post" style="margin-top:16px">
      <div style="display:flex;gap:12px;align-items:center;">
        <input type="text" name="id" placeholder="Mã người dùng" required>
        <input type="text" name="fullname" placeholder="Họ tên" required>
        <input type="text" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Mật khẩu" required>
        <select name="role">
          <option value="1">Quản trị</option>
          <option value="0">Phóng viên</option>
        </select>
        <button type="submit" class="button">Thêm</button>
      </div>
    </form>

    <div style="margin-top:16px;">
      <a class="button" href="approve_reporters.jsp">Duyệt phóng viên</a>
    </div>

    <!-- Danh sách người dùng -->
    <table style="width:100%;margin-top:16px;border-collapse:collapse">
      <thead><tr>
        <th>Mã</th><th>Họ tên</th><th>Email</th><th>Vai trò</th>
      </tr></thead>
      <tbody>
        <c:forEach var="u" items="${users}">
          <tr>
            <td>${u.id}</td>
            <td>${u.fullname}</td>
            <td>${u.email}</td>
            <td><c:choose>
              <c:when test="${u.role}">Quản trị</c:when>
              <c:otherwise>Phóng viên</c:otherwise>
            </c:choose></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
