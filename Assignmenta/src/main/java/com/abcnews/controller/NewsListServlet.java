package com.abcnews.controller;

import java.io.IOException;
import java.util.List;

import com.abcnews.dao.CategoryDAO;
import com.abcnews.dao.NewsDAO;
import com.abcnews.model.Category;
import com.abcnews.model.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "NewsListServlet", urlPatterns = {"/news-list"})
public class NewsListServlet extends HttpServlet {
    private final NewsDAO dao = new NewsDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<News> list = dao.findAll();
            List<News> latest = dao.findLatest(5); // 🆕 Tin mới nhất
            List<Category> cats = categoryDAO.findAll(); // 🆕 Danh mục

            request.setAttribute("newsList", list);
            request.setAttribute("latestNews", latest);
            request.setAttribute("categories", cats);

            request.getRequestDispatcher("/views/index/news_list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Lỗi khi lấy danh sách tin: " + e.getMessage());
        }
    }
}
