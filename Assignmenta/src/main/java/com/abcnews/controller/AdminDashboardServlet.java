package com.abcnews.controller;

import com.abcnews.dao.NewsDAO;
import com.abcnews.model.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin-dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    private final NewsDAO dao = new NewsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<News> pendingNews = dao.findPending(); // tin chưa duyệt
            req.setAttribute("pendingNews", pendingNews);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Không tải được danh sách tin chờ duyệt: " + e.getMessage());
        }

        req.getRequestDispatcher("/views/admin/admin_dashboard.jsp").forward(req, resp);
    }
}

