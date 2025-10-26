package com.abcnews.controller;

import com.abcnews.dao.CategoryDAO;
import com.abcnews.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet",
        urlPatterns = {
            "/quan-ly-loai-tin",
            "/quan-ly-loai-tin/them",
            "/quan-ly-loai-tin/sua",
            "/quan-ly-loai-tin/xoa"
        })
public class CategoryServlet extends HttpServlet {

    private CategoryDAO dao = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        try {
            if (uri.endsWith("/quan-ly-loai-tin")) {
                // Hiển thị danh sách loại tin
            	if (uri.endsWith("/quan-ly-loai-tin")) {
            	    List<Category> list = dao.findAll();
            	    request.setAttribute("categories", list);
            	    request.getRequestDispatcher("/views/admin/admin_categories.jsp")
            	           .forward(request, response);
            	}



            } else if (uri.endsWith("/quan-ly-loai-tin/sua")) {
                String id = request.getParameter("id");
                Category c = dao.findById(id);
                request.setAttribute("category", c);
                request.getRequestDispatcher("/views/admin/admin_categories.jsp")
                .forward(request, response);


            } else if (uri.endsWith("/quan-ly-loai-tin/xoa")) {
                String id = request.getParameter("id");
                dao.delete(id);
                response.sendRedirect(request.getContextPath() + "/quan-ly-loai-tin");
            }

        } catch (Exception e) {
            throw new ServletException("Lỗi xử lý loại tin", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        try {
            if (uri.endsWith("/quan-ly-loai-tin/them")) {
                Category c = new Category();
                c.setId(request.getParameter("id"));
                c.setName(request.getParameter("name"));
                dao.insert(c);
                response.sendRedirect(request.getContextPath() + "/quan-ly-loai-tin");

            } else if (uri.endsWith("/quan-ly-loai-tin/sua")) {
                Category c = new Category();
                c.setId(request.getParameter("id"));
                c.setName(request.getParameter("name"));
                dao.update(c);
                response.sendRedirect(request.getContextPath() + "/quan-ly-loai-tin");
            }

        } catch (Exception e) {
            throw new ServletException("Lỗi thêm/cập nhật loại tin", e);
        }
    }
}

