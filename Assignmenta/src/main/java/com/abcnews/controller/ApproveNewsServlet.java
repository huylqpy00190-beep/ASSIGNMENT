package com.abcnews.controller;

import com.abcnews.dao.NewsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name="ApproveNewsServlet", urlPatterns = {"/quan-ly-tin/duyet-bai"})
public class ApproveNewsServlet extends HttpServlet {
    private final NewsDAO dao = new NewsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/quan-ly-tin?error=missing_id");
            return;
        }
        try {
            dao.approveNews(id, true);
            resp.sendRedirect(req.getContextPath() + "/quan-ly-tin?message=approved");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

