<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
  <div class="card">
    <h2>Quản lý tin</h2>
    <div style="margin-top:12px">
      <table style="width:100%;border-collapse:collapse">
        <thead>
          <tr style="text-align:left;color:var(--muted)">
            <th style="padding:8px">Tiêu đề</th>
            <th style="padding:8px">Ngày</th>
            <th style="padding:8px">Lượt xem</th>
            <th style="padding:8px">Hành động</th>
          </tr>
        </thead>
        <tbody>
          <% java.util.List myNews = (java.util.List) request.getAttribute("myNews");
             if(myNews!=null){
               for(Object o: myNews){
                 java.util.Map n=(java.util.Map)o;
          %>
            <tr>
              <td style="padding:8px">
                <a href="${pageContext.request.contextPath}/chi-tiet?id=<%=n.get("id")%>"><%=n.get("title")%></a>
              </td>
              <td style="padding:8px"><fmt:formatDate value="${n.postedDateAsDate}" pattern="dd/MM/yyyy HH:mm"/></td>
              <td style="padding:8px"><%=n.get("viewCount")%></td>
              <td style="padding:8px">
                <<a class="button secondary"
				   href="${pageContext.request.contextPath}/pending-news?action=delete&id=<%=n.get("id")%>"
				   onclick="return confirm('Bạn có muốn xóa bài viết này không?')">Xóa</a>

              </td>
            </tr>
          <% } } else { %>
            <tr><td colspan="4" style="padding:12px">Bạn chưa có bài viết nào.</td></tr>
          <% } %>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>