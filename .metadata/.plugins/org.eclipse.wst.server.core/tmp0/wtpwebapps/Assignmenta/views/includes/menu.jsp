<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<div class="card">
    <h3>Danh mục</h3>
    <ul style="padding-left:0; margin-top:12px;">
    
    <%-- 1. Sử dụng <c:choose> để kiểm tra danh sách có rỗng không --%>
    <c:choose>
        <%-- Lấy đối tượng List<Category> từ request scope bằng EL: ${categories} --%>
        <c:when test="${not empty categories}">
            <%-- 2. Sử dụng <c:forEach> để lặp qua danh sách --%>
            <c:forEach var="cat" items="${categories}">
                <li style="list-style:none;margin:8px 0;">
                    <%-- 3. Dùng EL để xây dựng URL và hiển thị dữ liệu --%>
                    <a href="${pageContext.request.contextPath}/danh-muc?category=${cat.id}">
                        ${cat.name}
                    </a>
                </li>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <li>Không có danh mục nào.</li>
        </c:otherwise>
    </c:choose>
    </ul>
</div>



