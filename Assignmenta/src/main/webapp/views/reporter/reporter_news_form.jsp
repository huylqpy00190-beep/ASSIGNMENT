<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.abcnews.model.Category" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Viết tin</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container" style="max-width:900px">
  <div class="card">
    <h2><%= "add".equals(request.getParameter("action"))? "Tạo bài mới":"Chỉnh sửa bài viết" %></h2>
    <form action="${pageContext.request.contextPath}/pending-news" method="post" enctype="multipart/form-data">
      <% String action = request.getParameter("action");
         if("edit".equals(action)){
      %>
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
      <% } else { %>
        <input type="hidden" name="action" value="create">
      <% } %>

      <div class="form-row">
        <label for="title">Tiêu đề</label>
        <input id="title" name="title" type="text"
               value="<%= request.getAttribute("title")==null?"":request.getAttribute("title") %>" required>
      </div>

      <div class="form-row">
        <label for="category">Loại tin</label>
        <select id="category" name="categoryId" required>
          <%
             java.util.List<Category> categories = (java.util.List<Category>) request.getAttribute("categories");
             if (categories != null) {
                 for (Category c : categories) {
          %>
            <option value="<%=c.getId()%>"><%=c.getName()%></option>
          <%
                 }
             } else {
          %>
            <option disabled>Không có danh mục nào</option>
          <%
             }
          %>
        </select>
      </div>

      <div class="form-row">
        <label for="image_file">Chọn hình ảnh</label>
        <input type="file" id="image_file" name="image" accept="image/*">
      </div>

      <div class="form-row">
        <label for="content">Nội dung</label>
        <textarea id="content" name="content" required>
          <%= request.getAttribute("content")==null?"":request.getAttribute("content")%>
        </textarea>
      </div>

      <div>
        <button class="button" type="submit">Lưu</button>
        <a class="button secondary" href="${pageContext.request.contextPath}/views/reporter/reporter_dashboard.jsp">Hủy</a>
      </div>
    </form>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>


