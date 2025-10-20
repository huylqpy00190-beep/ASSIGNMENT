<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="includes/header.jsp"/>
<div class="container">
  <div class="card">
    <h2>Quản lý tin</h2>
    <div style="margin-top:12px">
      <a class="button" href="<%=request.getContextPath()%>/admin/news?action=add">Thêm tin</a>
    </div>
    <div style="margin-top:12px">
      <table style="width:100%;border-collapse:collapse">
        <thead><tr style="color:var(--muted)"><th style="padding:8px">Tiêu đề</th><th style="padding:8px">Tác giả</th><th style="padding:8px">Ngày</th><th style="padding:8px">Hành động</th></tr></thead>
        <tbody>
          <% java.util.List newsAll = (java.util.List) request.getAttribute("newsAll");
             if(newsAll!=null){
               for(Object o:newsAll){
                 java.util.Map n=(java.util.Map)o;
          %>
            <tr>
              <td style="padding:8px"><%=n.get("title")%></td>
              <td style="padding:8px"><%=n.get("authorName")%></td>
              <td style="padding:8px"><%=n.get("postedDate")%></td>
              <td style="padding:8px">
                <a class="button secondary" href="<%=request.getContextPath()%>/admin/news?action=edit&id=<%=n.get("id")%>">Sửa</a>
                <a class="button secondary" href="<%=request.getContextPath()%>/admin/news?action=delete&id=<%=n.get("id")%>" onclick="return confirm('Xóa bài viết?')">Xóa</a>
              </td>
            </tr>
          <% } } else { %>
            <tr><td colspan="4" style="padding:12px">Chưa có bài viết.</td></tr>
          <% } %>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="includes/footer.jsp"/>
