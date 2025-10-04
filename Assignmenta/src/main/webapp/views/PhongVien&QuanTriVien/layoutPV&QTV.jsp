<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${pageTitle != null ? pageTitle : "Quản trị tin tức"}</title>
    <link rel="stylesheet" href="../../Css/style.css">
</head>
<body>
    <!-- Header -->
    <header class="header">
        <h2>CÔNG CỤ QUẢN TRỊ TIN TỨC</h2>
    </header>

    <!-- Navigation -->
    <nav class="nav">
        <a href="adminHome.jsp">Trang chủ</a> |
        <a href="news.jsp">Tin tức</a> |
        <a href="category.jsp">Loại tin</a> |
        <a href="user.jsp">Người dùng</a> |
        <a href="newsletter.jsp">Newsletter</a>
    </nav>

    <!-- Main content -->
    <main class="container">
        <jsp:include page="${contentPage}" />
    </main>

    <!-- Footer -->
    <footer class="footer">
        Welcome <c:out value="${sessionScope.username != null ? sessionScope.username : 'Khách'}" />
    </footer>
</body>
</html>