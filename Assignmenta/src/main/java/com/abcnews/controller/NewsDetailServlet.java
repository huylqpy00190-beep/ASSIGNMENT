package com.abcnews.controller;

import com.abcnews.dao.NewsDAO;
import com.abcnews.model.News;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/chi-tiet") 
public class NewsDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final NewsDAO newsDAO = new NewsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String id = request.getParameter("id");
        
        if (id == null || id.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/trang-chu");
            return;
        }
        
        try {
            News n = newsDAO.findById(id);
            
            if (n != null) { // ĐÃ SỬA: CHỈ KIỂM TRA TIN CÓ TỒN TẠI KHÔNG
                
                // 1. Tăng lượt xem (không cần kiểm tra isApproved())
                newsDAO.increaseViewCount(id);
                
                // 2. Lấy dữ liệu cho Sidebar và Footer
                int limit = 5;
                List<News> relatedNews = newsDAO.findRelatedNews(n.getCategoryId(), n.getId(), limit);
                List<News> hotSidebar = newsDAO.findHotSidebarNews(limit);
                
                // 3. Đặt các thuộc tính cần thiết vào request
                request.setAttribute("n", n); 
                request.setAttribute("related", relatedNews);
                request.setAttribute("hotSidebar", hotSidebar); 
                
                // 4. Chuyển tiếp đến JSP
                request.getRequestDispatcher("/views/index/news_detail.jsp").forward(request, response);
                
            } else {
                // Tin không tồn tại
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tin tức không tồn tại.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi tải chi tiết tin tức.", e);
        }
    }
}
