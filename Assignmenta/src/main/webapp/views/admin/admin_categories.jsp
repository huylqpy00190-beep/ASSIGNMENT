<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý loại tin</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>

<div class="container">
  <div class="card">
    <h2>Quản lý loại tin</h2>

    <!-- Form thêm loại tin -->
    <form action="${pageContext.request.contextPath}/quan-ly-loai-tin/them" method="post" style="margin-top:16px">
      <div style="display:flex;gap:12px;align-items:center;">
        <div>
          <label for="id">Mã loại tin:</label><br>
          <input type="text" id="id" name="id" required style="padding:6px;width:160px">
        </div>
        <div>
          <label for="name">Tên loại tin:</label><br>
          <input type="text" id="name" name="name" required style="padding:6px;width:240px">
        </div>
        <div style="margin-top:18px;">
          <button type="submit" class="button">Thêm</button>
        </div>
      </div>
    </form>

    <!-- Bảng danh sách loại tin -->
    <div style="margin-top:24px">
      <table style="width:100%;border-collapse:collapse">
        <thead>
          <tr style="color:var(--muted)">
            <th style="padding:8px">Mã</th>
            <th style="padding:8px">Tên</th>
            <th style="padding:8px">Hành động</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${not empty categories}">
              <c:forEach var="c" items="${categories}">
                <tr>
                  <td style="padding:8px">${c.id}</td>
                  <td style="padding:8px">${c.name}</td>
                  <td style="padding:8px">
                    <a class="button secondary"
                       href="${pageContext.request.contextPath}/quan-ly-loai-tin/sua?id=${c.id}">Sửa</a>
                    <a class="button secondary"
                       href="${pageContext.request.contextPath}/quan-ly-loai-tin/xoa?id=${c.id}"
                       onclick="return confirm('Xóa loại tin này?')">Xóa</a>
                  </td>
                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr><td colspan="3" style="padding:12px">Chưa có loại tin.</td></tr>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>
    </div>
  </div>
</div>

<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
