package com.abcnews.controller;

import com.abcnews.dao.CategoryDAO;
import com.abcnews.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = {"/quan-ly-loai-tin", "/quan-ly-loai-tin/them", "/quan-ly-loai-tin/sua", "/quan-ly-loai-tin/xoa"})
public class CategoryServlet extends HttpServlet {
    private final CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        try {
            if (uri.endsWith("/quan-ly-loai-tin")) {
                List<Category> list = dao.findAll();
                req.setAttribute("categories", list);
                req.getRequestDispatcher("views/admin/admin_categories.jsp").forward(req, resp);
            } else if (uri.endsWith("/quan-ly-loai-tin/sua")) {
                String id = req.getParameter("id");
                Category c = dao.findById(id);
                req.setAttribute("category", c);
                req.getRequestDispatcher("views/admin/admin_categories.jsp").forward(req, resp);
            } else if (uri.endsWith("/quan-ly-loai-tin/xoa")) {
                String id = req.getParameter("id");
                if (id != null && !id.isBlank()) dao.delete(id);
                resp.sendRedirect(req.getContextPath() + "/quan-ly-loai-tin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/quan-ly-loai-tin");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uri = req.getRequestURI();
        try {
            if (uri.endsWith("/quan-ly-loai-tin/them")) {
                Category c = new Category();
                c.setId(req.getParameter("id"));
                c.setName(req.getParameter("name"));
                dao.insert(c);
                resp.sendRedirect(req.getContextPath() + "/quan-ly-loai-tin");
            } else if (uri.endsWith("/quan-ly-loai-tin/sua")) {
                Category c = new Category();
                c.setId(req.getParameter("id"));
                c.setName(req.getParameter("name"));
                dao.update(c);
                resp.sendRedirect(req.getContextPath() + "/quan-ly-loai-tin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/quan-ly-loai-tin");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
