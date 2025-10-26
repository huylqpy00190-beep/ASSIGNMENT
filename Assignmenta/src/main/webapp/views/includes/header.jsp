<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : 'vi'}" />
<fmt:setBundle basename="global" />

<div class="header">
  <div class="container">
    <div class="top">
      <div class="logo">
        <div class="mark">ABC</div>
        <div>
          <div style="font-size:14px;opacity:.95">ABC News</div>
          <div style="font-size:12px;opacity:.85">Tin tức trực tuyến</div>
        </div>
      </div>

      <div style="text-align:right">
        <%-- Nếu có user trong session thì hiển thị tên và nút đăng xuất --%>
        <%
          Object userObj = session.getAttribute("user");
          if (userObj != null) {
              java.util.Map userMap = (java.util.Map) userObj;
              String fullname = (String) userMap.get("fullname");
        %>
              <div style="font-weight:600">Xin chào, <%= fullname %></div>
             <a href="${pageContext.request.contextPath}/dang-xuat" 
			   style="color:white;text-decoration:underline;font-size:13px">Đăng xuất</a>

        <%  } else { %>
              <a href="../index/login.jsp" style="color:white;text-decoration:underline;font-size:13px">
                <fmt:message key="global.login"/>
              </a>
              <span style="margin-left:10px;font-size:13px">
                <a href="?lang=en">English</a> | <a href="?lang=vi">Tiếng Việt</a>
              </span>
        <%  } %>
      </div>
    </div>

    <div class="nav" style="margin-top:12px;">
      <a href="../index/index.jsp"><fmt:message key="global.home"/></a>
      <a href="../index/news_list.jsp"><fmt:message key="global.news"/></a>
      <a href="../index/newsletter.jsp"><fmt:message key="global.signup"/></a>

      <%
  if (session.getAttribute("user") != null) {
      java.util.Map userMap = (java.util.Map) session.getAttribute("user");
      Object roleObj = userMap.get("role");

      boolean isAdmin = false;
      if (roleObj instanceof Boolean) {
          isAdmin = (Boolean) roleObj;
      } else if (roleObj instanceof Number) {
          isAdmin = ((Number) roleObj).intValue() == 1;
      } else if (roleObj instanceof String) {
          isAdmin = "1".equals(roleObj) || "true".equalsIgnoreCase((String) roleObj);
      }

      if (isAdmin) {
%>
          <a href="../admin/admin_dashboard.jsp">Admin</a>
<%
      } else {
%>
          <a href="../reporter/reporter_dashboard.jsp">Phóng viên</a>
<%
      }
  }
%>
    </div>
  </div>
</div>
