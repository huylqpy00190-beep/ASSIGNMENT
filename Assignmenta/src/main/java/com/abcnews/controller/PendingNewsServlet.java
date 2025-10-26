
package com.abcnews.controller;

import com.abcnews.utils.UploadUtils;
import com.abcnews.dao.NewsDAO;
import com.abcnews.model.News;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "PendingNewsServlet", urlPatterns = {"/pending-news"})
public class PendingNewsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String categoryId = request.getParameter("categoryId");
            String author = (String) request.getSession().getAttribute("userEmail");

            Part imgPart = request.getPart("image");
            String uploadPath = getServletContext().getRealPath("/uploads/pending");
            String filename = UploadUtils.saveImage(imgPart, uploadPath);

            News n = new News();
            String id = UUID.randomUUID().toString();
            n.setId(id);
            n.setTitle(title);
            n.setContent(content);
            n.setAuthor(author==null? "unknown": author);
            n.setCategoryId(categoryId);
            n.setImage(filename);
            n.setViewCount(0);
            n.setHome(false);

            NewsDAO dao = new NewsDAO();
            dao.insert(n);

            response.sendRedirect(request.getContextPath() + "/views/reporter/reporter_dashboard.jsp?msg=submitted");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
