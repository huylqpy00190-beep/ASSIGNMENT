<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page import="com.abcnews.model.News" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Danh sách tin</title>
<link rel="stylesheet" href="${pageContext.	request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
  <div class="main">
    <div>
      <div class="card">
        <h2>Danh sách tin</h2>
        <%
           // Lấy danh sách news từ request (do servlet setAttribute)
           java.util.List<News> newsList = (java.util.List<News>) request.getAttribute("newsList");
           if(newsList != null && !newsList.isEmpty()){
               for(News n : newsList){
        %>
          <div class="news-item">
            <img class="news-thumb" src="${pageContext.request.contextPath}/uploads/<%=n.getImage()%>" alt="">
            <div class="news-meta">
              <h3><a href="${pageContext.request.contextPath}/chi-tiet?id=<%=n.getId()%>"><%=n.getTitle()%></a></h3>
              <div class="meta">
                <fmt:formatDate value="<%=n.getPostedDate() != null ? java.sql.Timestamp.valueOf(n.getPostedDate()) : null %>" pattern="dd/MM/yyyy HH:mm"/>
                • <%= n.getAuthor() != null ? n.getAuthor() : "" %>
              </div>
              <p style="margin-top:8px;color:#444">
                <%= n.getContent() != null && n.getContent().length() > 100 ? n.getContent().substring(0, 100) + "..." : n.getContent() %>
              </p>
            </div>
          </div>
        <% } } else { %>
          <p>Không có tin cho danh mục này.</p>
        <% } %>
      </div>
    </div>

    <aside class="sidebar">
      <jsp:include page="../includes/menu.jsp"/>
      <div style="height:16px"></div>
      <div class="card">
        <h3>Tin mới nhất</h3>
        <%
           java.util.List<News> latest = (java.util.List<News>) request.getAttribute("latestNews");
           if(latest != null){
               for(News n : latest){
        %>
          <div style="padding:8px 0;border-bottom:1px solid #f1f1f1">
            <a href="${pageContext.request.contextPath}/chi-tiet?id=<%=n.getId()%>"><%=n.getTitle()%></a>
          </div>
        <% } } %>
      </div>
    </aside>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
