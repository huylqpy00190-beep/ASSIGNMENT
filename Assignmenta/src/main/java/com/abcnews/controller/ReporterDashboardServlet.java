package com.abcnews.controller;

import com.abcnews.dao.NewsDAO;
import com.abcnews.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name="ReporterDashboardServlet", urlPatterns={"/reporter-dashboard"})
public class ReporterDashboardServlet extends HttpServlet {
    private final NewsDAO dao = new NewsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");

        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/dang-nhap");
            return;
        }

        try {
            // ✅ Lấy tất cả tin của phóng viên này
            List<Map<String, Object>> myNews = dao.findByAuthor(u.getId());
            req.setAttribute("myNews", myNews);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Không thể tải danh sách bài viết: " + e.getMessage());
        }

        req.getRequestDispatcher("/views/reporter/reporter_dashboard.jsp").forward(req, resp);
    }
}

