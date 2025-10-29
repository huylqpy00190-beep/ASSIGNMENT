<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <%-- Bổ sung JSTL Core --%>

<%-- Đảm bảo model News được import nếu cần scriptlet, nhưng chúng ta sẽ dùng EL --%>
<%-- <%@ page import="com.abcnews.model.News" %> --%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chi tiết tin</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
  <div class="main">
    <div>
      <div class="card news-detail">
        
        <%-- KIỂM TRA ĐỐI TƯỢNG 'n' (News) được setAttribute trong Servlet --%>
        <c:if test="${not empty n}">
          <%-- Sửa lỗi: Truy cập thuộc tính trực tiếp từ đối tượng News (n) --%>
          <h1>${n.title}</h1>
          <div class="meta">
            <%-- Dùng n.postedDateAsUtilDate đã sửa trong News.java (nếu bạn đã thêm) --%>
            <fmt:formatDate value="${n.postedDateAsUtilDate}" pattern="dd/MM/yyyy HH:mm"/>
            • ${n.author} 
            • ${n.viewCount} lượt xem
          </div>
          <div style="height:12px"></div>
          <img src="${pageContext.request.contextPath}/uploads/${n.image}" alt="" style="width:100%;max-height:420px;object-fit:cover;border-radius:8px">
          <div class="content" style="margin-top:14px">${n.content}</div>

          <div style="height:18px"></div>
          <h3>Các tin cùng loại</h3>
          <div>
            <%-- Lặp qua danh sách tin liên quan (List<News>) --%>
            <c:choose>
              <c:when test="${not empty related}">
                <c:forEach var="r" items="${related}">
                  <div style="margin:8px 0;"><a href="${pageContext.request.contextPath}/chi-tiet?id=${r.id}">${r.title}</a></div>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <p>Không có tin liên quan.</p>
              </c:otherwise>
            </c:choose>
          </div>
        </c:if> 
        <c:if test="${empty n}">
          <p>Tin không tồn tại.</p>
        </c:if>
      </div>
    </div>

    <aside class="sidebar">
      <jsp:include page="../includes/menu.jsp"/>
      <div style="height:16px"></div>
      <div class="card">
        <h3>Tin nổi bật</h3>
        <%-- Lặp qua danh sách tin nổi bật (List<News>) --%>
        <c:choose>
          <c:when test="${not empty hotSidebar}">
            <c:forEach var="n" items="${hotSidebar}">
              <div style="padding:8px 0;"><a href="${pageContext.request.contextPath}/chi-tiet?id=${n.id}">${n.title}</a></div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <p>Không có tin nổi bật.</p>
          </c:otherwise>
        </c:choose>
      </div>
    </aside>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
