<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Quản lý Newsletter</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>

  <jsp:include page="../includes/header.jsp"/>
y
  <div class="container">
    <div class="card">
      <h2>Quản lý Newsletter</h2>
      <div style="margin-top:12px">
        <table style="width:100%;border-collapse:collapse">
          <thead>
            <tr style="color:var(--muted)">
              <th style="padding:8px">Email</th>
              <th style="padding:8px">Trạng thái</th>
              <th style="padding:8px">Hành động</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${not empty newsletters}">
                <c:forEach var="s" items="${newsletters}">
                  <tr>
                    <td style="padding:8px">${s.email}</td>
                    <td style="padding:8px">
                      <c:choose>
                        <c:when test="${s.enabled}">Đang kích hoạt</c:when>
                        <c:otherwise>Đã huỷ</c:otherwise>
                      </c:choose>
                    </td>
                    <td style="padding:8px">
                      <form action="${pageContext.request.contextPath}/admin/newsletters" method="post" style="display:inline">
                        <input type="hidden" name="action" value="toggle">
                        <input type="hidden" name="email" value="${s.email}">
                        <button class="button secondary" type="submit">Bật/Tắt</button>
                      </form>
                      <a class="button secondary" 
                         href="${pageContext.request.contextPath}/admin/newsletters?action=delete&email=${s.email}" 
                         onclick="return confirm('Xóa email này?')">Xóa</a>
                    </td>
                  </tr>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <tr><td colspan="3" style="padding:12px;text-align:center">Chưa có đăng ký.</td></tr>
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
