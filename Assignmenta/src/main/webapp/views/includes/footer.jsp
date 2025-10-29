<%@ page import="com.abcnews.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="footer">
  <div class="container">
    <div>© <%= java.time.Year.now() %> ABC News. All rights reserved.</div>
    <div style="font-size:13px;margin-top:6px;">
      <%
        User user = (User) session.getAttribute("user");
        if (user != null) {
      %>
          Người dùng: <%= user.getFullname() != null ? user.getFullname() : user.getId() %>
      <%
        } else {
      %>
          Bạn chưa đăng nhập.
      <%
        }
      %>
    </div>
  </div>
</div>




