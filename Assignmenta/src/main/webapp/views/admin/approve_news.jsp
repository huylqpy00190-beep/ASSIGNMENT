<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Duyệt bài viết</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>

<div class="container">
  <div class="card">
    <h2>Bài viết chờ duyệt</h2>

    <table style="width:100%;border-collapse:collapse;margin-top:12px;">
      <thead><tr><th>Tiêu đề</th><th>Tác giả</th><th>Ngày đăng</th><th>Thao tác</th></tr></thead>
      <tbody>
        <c:forEach var="n" items="${pendingNews}">
          <tr>
            <td>${n.title}</td>
            <td>${n.author}</td>
            <td><fmt:formatDate value="${n.postedDateAsDate}" pattern="dd/MM/yyyy HH:mm"/></td>
            <td>
              <form action="${pageContext.request.contextPath}/quan-ly-tin/duyet-bai" method="post">
                <input type="hidden" name="id" value="${n.id}">
                <button type="submit" class="button">Phê duyệt</button>
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

