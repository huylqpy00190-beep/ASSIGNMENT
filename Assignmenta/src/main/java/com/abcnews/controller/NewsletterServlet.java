package com.abcnews.controller;

import com.abcnews.dao.NewsletterDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name="NewsletterServlet", urlPatterns = {"/newsletter/subscribe","/admin/newsletters"})
public class NewsletterServlet extends HttpServlet {
    private final NewsletterDAO dao = new NewsletterDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        try {
            String email = request.getParameter("email");
            if (email == null || email.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Thiếu địa chỉ email!");
                return;
            }

            dao.subscribe(email); // ✅ gọi đúng hàm trong DAO
            response.getWriter().write("Đăng ký nhận tin thành công!");
            request.getRequestDispatcher("/views/index/newsletter.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Lỗi khi đăng ký nhận tin: " + e.getMessage());
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị form đăng ký (JSP)
        request.getRequestDispatcher("/views/index/newsletter.jsp").forward(request, response);
    }
}
