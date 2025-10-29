package com.abcnews.controller;

import com.abcnews.dao.CategoryDAO;
import com.abcnews.dao.NewsDAO;
import com.abcnews.model.Category;
import com.abcnews.model.News;
import com.abcnews.utils.UploadUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "PendingNewsServlet", urlPatterns = {"/pending-news"})
public class PendingNewsServlet extends HttpServlet {

    // ✅ Khi người dùng bấm “Viết tin mới”
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    try {
	        String action = request.getParameter("action");

	        if ("add".equals(action)) {
	            // ✅ Lấy danh mục tin từ DB
	            CategoryDAO dao = new CategoryDAO();
	            List<Category> categories = dao.findAll();

	            // Gửi sang form tạo mới
	            request.setAttribute("categories", categories);
	            request.getRequestDispatcher("/views/reporter/reporter_news_form.jsp")
	                   .forward(request, response);

	        } else if ("delete".equals(action)) {
	            // ✅ Xóa bài viết
	            String id = request.getParameter("id");
	            if (id != null && !id.isEmpty()) {
	                NewsDAO dao = new NewsDAO();
	                dao.delete(id);
	            }
	            response.sendRedirect(request.getContextPath() + "/views/reporter/reporter_dashboard.jsp?msg=deleted");

	        } else {
	            // ✅ Mặc định quay lại dashboard
	            response.sendRedirect(request.getContextPath() + "/views/reporter/reporter_dashboard.jsp");
	        }

	    } catch (Exception e) {
	        throw new ServletException("Lỗi khi tải danh sách hoặc xử lý yêu cầu: " + e.getMessage(), e);
	    }
	}


    // ✅ Khi người dùng nhấn “Lưu”
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String categoryId = request.getParameter("categoryId");

            // ✅ Lấy thông tin user đang đăng nhập từ session
            HttpSession session = request.getSession();
            com.abcnews.model.User user = (com.abcnews.model.User) session.getAttribute("user");

            if (user == null || user.getId() == null) {
                throw new ServletException("Không tìm thấy ID người dùng trong session (authorId=null)");
            }

            String authorId = user.getId();

            // ✅ Xử lý ảnh
            Part imgPart = request.getPart("image");
            String uploadPath = request.getServletContext().getRealPath("/uploads");
            String filename = com.abcnews.utils.UploadUtils.saveImage(imgPart, uploadPath);

            // ✅ Tạo đối tượng tin tức
            com.abcnews.model.News n = new com.abcnews.model.News();
            n.setId(java.util.UUID.randomUUID().toString());
            n.setTitle(title);
            n.setContent(content);
            n.setAuthor(authorId);  // Dùng ID của người dùng, đúng FK trong DB
            n.setCategoryId(categoryId);
            n.setImage(filename);
            n.setViewCount(0);
            n.setHome(false);
            n.setApproved(false);
            n.setPostedDate(java.time.LocalDateTime.now());

            // ✅ Lưu tin vào DB
            com.abcnews.dao.NewsDAO dao = new com.abcnews.dao.NewsDAO();
            dao.insert(n);

            response.sendRedirect(request.getContextPath() + "/views/reporter/reporter_dashboard.jsp?msg=submitted");

        } catch (Exception e) {
            throw new ServletException("Lỗi khi lưu tin mới", e);
        }
    }


}

