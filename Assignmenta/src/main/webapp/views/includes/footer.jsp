<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="footer">
  <div class="container">
    <div>© <%= java.time.Year.now() %> ABC News. All rights reserved.</div>
    <div style="font-size:13px;margin-top:6px;">
      <% if(session.getAttribute("user") != null){ %>
        Người dùng: <%= ((java.util.Map)session.getAttribute("user")).get("fullname") %>
      <% } else { %>
        Bạn chưa đăng nhập.
      <% } %>
    </div>
  </div>
</div>
</body>
</html>
