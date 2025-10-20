<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="includes/header.jsp"/>
<div class="container">
  <div class="main">
    <div>
      <div class="card">
        <h2>Tin trang nhất</h2>
        <div style="display:grid;grid-template-columns:repeat(2,1fr);gap:12px;margin-top:12px;">
          <% java.util.List hot = (java.util.List) request.getAttribute("homeNews");
             if(hot!=null){
               for(Object o:hot){
                 java.util.Map n = (java.util.Map)o;
          %>
           <div style="display:flex;gap:12px;align-items:flex-start;">
             <img src="<%=n.get("image")%>" class="news-thumb" alt="">
             <div>
               <h3><a href="<%=request.getContextPath()%>/news_detail.jsp?id=<%=n.get("id")%>"><%=n.get("title")%></a></h3>
               <div class="meta"><%=n.get("postedDate")%> - <%=n.get("viewCount")%> lượt xem</div>
             </div>
           </div>
          <% }} else { %>
            <p>Không có tin trang nhất.</p>
          <% } %>
        </div>
      </div>

      <div style="height:16px"></div>

      <div class="card">
        <h2>Tin mới nhất</h2>
        <% java.util.List latest = (java.util.List) request.getAttribute("latestNews");
           if(latest!=null){
             for(Object o:latest){
               java.util.Map n = (java.util.Map)o;
        %>
          <div class="news-item">
            <img class="news-thumb" src="<%=n.get("image")%>" alt="">
            <div class="news-meta">
              <h3><a href="<%=request.getContextPath()%>/news_detail.jsp?id=<%=n.get("id")%>"><%=n.get("title")%></a></h3>
              <div class="meta"><%=n.get("postedDate")%> - <%=n.get("categoryName")%></div>
            </div>
          </div>
        <% } } else { %>
          <p>Không có tin mới.</p>
        <% } %>
      </div>
    </div>

    <aside class="sidebar">
      <div class="card">
        <h3>Tin nổi bật</h3>
        <ul class="small-list" style="padding-left:0;margin-top:8px;">
          <% java.util.List hot5 = (java.util.List) request.getAttribute("hotNews");
             if(hot5!=null){
               for(Object o: hot5){
                 java.util.Map n = (java.util.Map)o;
          %>
           <li><a href="<%=request.getContextPath()%>/news_detail.jsp?id=<%=n.get("id")%>"><%=n.get("title")%></a></li>
          <% } } else { %>
            <li>Không có tin nổi bật.</li>
          <% } %>
        </ul>
      </div>

      <div style="height:16px"></div>

      <div class="card">
        <h3>Đăng ký nhận tin</h3>
        <form action="<%=request.getContextPath()%>/newsletter" method="post">
          <div class="form-row">
            <label for="email">Email</label>
            <input id="email" name="email" type="email" placeholder="you@example.com" required>
          </div>
          <button class="button" type="submit">Đăng ký</button>
        </form>
      </div>
    </aside>
  </div>
</div>

<jsp:include page="includes/footer.jsp"/>
</body>
</html>

