<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp"/>
<div class="container">
  <div class="card">
    <h2>Quản lý Newsletter</h2>
    <div style="margin-top:12px">
      <table style="width:100%;border-collapse:collapse">
        <thead><tr style="color:var(--muted)"><th style="padding:8px">Email</th><th style="padding:8px">Trạng thái</th><th style="padding:8px">Hành động</th></tr></thead>
        <tbody>
          <% java.util.List newsletters = (java.util.List) request.getAttribute("newsletters");
             if(newsletters!=null){
               for(Object o:newsletters){
                 java.util.Map s=(java.util.Map)o;
          %>
            <tr>
              <td style="padding:8px"><%=s.get("email")%></td>
              <td style="padding:8px"><%= Boolean.TRUE.equals(s.get("enabled")) ? "Đang kích hoạt" : "Đã huỷ" %></td>
              <td style="padding:8px">
                <a class="button secondary" href="/newsletter/subscribe">Bật/Tắt</a>
                <a class="button secondary" href="<%=request.getContextPath()%>/admin/newsletters?action=delete&email=<%=s.get("email")%>" onclick="return confirm('Xóa email?')">Xóa</a>
              </td>
            </tr>
          <% } } else { %>
            <tr><td colspan="3" style="padding:12px">Chưa có đăng ký.</td></tr>
          <% } %>
        </tbody>
      </table>
    </div>
  </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>