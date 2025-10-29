<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ABC News - Trang chủ</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
  <div class="main">
    <div>
      <div class="card">
        <h2>Tin trang nhất</h2>
        <div style="display:grid;grid-template-columns:repeat(2,1fr);gap:12px;margin-top:12px;">
          <c:choose>
            <c:when test="${not empty homeNews}">
              <c:forEach var="n" items="${homeNews}">
               <div style="display:flex;gap:12px;align-items:flex-start;">
                 <img src="${pageContext.request.contextPath}/uploads/${n.image}" class="news-thumb" alt="">
                 <div>
                   <h3><a href="${pageContext.request.contextPath}/chi-tiet?id=${n.id}">${n.title}</a></h3>
                   <div class="meta">
					    <fmt:formatDate value="${n.postedDateAsUtilDate}" pattern="dd/MM/yyyy HH:mm"/>
					    - ${n.viewCount} lượt xem
					</div>
                 </div>
               </div>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <p>Không có tin trang nhất.</p>
            </c:otherwise>
          </c:choose>
        </div>
      </div>

      <div style="height:16px"></div>

      <div class="card">
        <h2>Tin mới nhất</h2>
        <c:choose>
          <c:when test="${not empty latestNews}">
            <c:forEach var="n" items="${latestNews}">
              <div class="news-item">
                <img class="news-thumb" src="${pageContext.request.contextPath}/uploads/${n.image}" alt="">
                <div class="news-meta">
                  <h3><a href="${pageContext.request.contextPath}/chi-tiet?id=${n.id}">${n.title}</a></h3>
                  <div class="meta">
					  <fmt:formatDate value="${n.postedDateAsUtilDate}" pattern="dd/MM/yyyy HH:mm"/>
					  - ${categoryNames[n.categoryId]}
					</div>
                </div>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <p>Không có tin mới.</p>
          </c:otherwise>
        </c:choose>
      </div>
    </div>

    <aside class="sidebar">
      <jsp:include page="../includes/menu.jsp"/>
      <div style="height:16px"></div>
      <div class="card">
        <h3>Tin nổi bật</h3>
        <ul class="small-list" style="padding-left:0;margin-top:8px;">
          <c:choose>
            <c:when test="${not empty hotNews}">
              <c:forEach var="n" items="${hotNews}">
               <li><a href="${pageContext.request.contextPath}/chi-tiet?id=${n.id}">${n.title}</a></li>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <li>Không có tin nổi bật.</li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>

      <div style="height:16px"></div>

      <div class="card">
        <h3>Đăng ký nhận tin</h3>
        <form action="${pageContext.request.contextPath}/newsletter/subscribe" method="post">
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

<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
