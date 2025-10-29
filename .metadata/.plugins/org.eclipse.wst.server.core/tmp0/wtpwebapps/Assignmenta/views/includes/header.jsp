<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="com.abcnews.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
        <% 
          User user = (User) session.getAttribute("user");
          if (user != null) { 
        %>
            <div style="font-weight:600;color:white">
              Xin chào, <%= user.getFullname() != null ? user.getFullname() : user.getId() %>
            </div>
            <a href="${pageContext.request.contextPath}/dang-xuat" 
               style="color:white;text-decoration:underline;font-size:13px">
               Đăng xuất
            </a>
        <% 
          } else { 
        %>
            <a href="${pageContext.request.contextPath}/dang-nhap" 
               style="color:white;text-decoration:underline;font-size:13px">
               <fmt:message key="global.login"/>
            </a>
            <span style="margin-left:10px;font-size:13px">
              <a href="?lang=en">English</a> | <a href="?lang=vi">Tiếng Việt</a>
            </span>
        <% 
          } 
        %>
      </div>
    </div>

    <div class="nav" style="margin-top:12px;">
      <a href="${pageContext.request.contextPath}/trang-chu"><fmt:message key="global.home"/></a>
      <a href="${pageContext.request.contextPath}/news-list"><fmt:message key="global.news"/></a>
      <a href="${pageContext.request.contextPath}/newsletter/subscribe"><fmt:message key="global.signup"/></a>

      <%
        if (user != null) {
            boolean isAdmin = user.isRole();
            if (isAdmin) {
      %>
              <a href="${pageContext.request.contextPath}/admin-dashboard">Admin</a>
      <%
            } else {
      %>
              <a href="${pageContext.request.contextPath}/reporter-dashboard">Phóng viên</a>
      <%
            }
        }
      %>
    </div>
  </div>
</div>
