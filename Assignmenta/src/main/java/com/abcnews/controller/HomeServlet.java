package com.abcnews.controller;

import com.abcnews.dao.CategoryDAO;
import com.abcnews.dao.NewsDAO;
import com.abcnews.model.Category;
import com.abcnews.model.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="HomeServlet", urlPatterns = {"/trang-chu", "/"})
public class HomeServlet extends HttpServlet {
    private final NewsDAO newsDAO = new NewsDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<News> top = newsDAO.getTopNews(5);
            List<News> home = newsDAO.findHomeNews();
            List<Category> cats = categoryDAO.findAll();
            List<News> latest = newsDAO.findLatest(3); // 🆕 Lấy 5 tin mới nhất
            request.setAttribute("topNews", top);
            request.setAttribute("homeNews", home);
            request.setAttribute("categories", cats);
            request.setAttribute("latestNews", latest);
            CategoryDAO catDAO = new CategoryDAO();
            List<Category> categories = catDAO.findAll();
            Map<String, String> categoryNames = new HashMap<>();
            for (Category c : categories) {
                categoryNames.put(c.getId(), c.getName());
            }
            request.setAttribute("categoryNames", categoryNames);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
        request.getRequestDispatcher("/views/index/index.jsp").forward(request, response);
    }
}
