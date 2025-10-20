package com.abcnews.controller;

import com.abcnews.dao.NewsDAO;
import com.abcnews.dao.CategoryDAO;
import com.abcnews.dao.NewsletterDAO;
import com.abcnews.model.News;
import com.abcnews.model.Category;
import com.abcnews.utils.UploadUtils;
import com.abcnews.utils.EmailUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name="NewsServlet", urlPatterns = {"/quan-ly-tin", "/quan-ly-tin/them", "/quan-ly-tin/sua", "/quan-ly-tin/xoa", "/chi-tiet"})
@MultipartConfig(fileSizeThreshold = 1024*1024, maxFileSize = 5*1024*1024, maxRequestSize = 10*1024*1024)
public class NewsServlet extends HttpServlet {
    private NewsDAO newsDAO = new NewsDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private NewsletterDAO newsletterDAO = new NewsletterDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        try {
            if (uri.endsWith("/quan-ly-tin")) {
                List<News> list = newsDAO.findAll();
                request.setAttribute("list", list);
                request.getRequestDispatcher("/WEB-INF/views/quan-ly-tin.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-tin/them")) {
                List<Category> cats = categoryDAO.findAll();
                request.setAttribute("cats", cats);
                request.getRequestDispatcher("/WEB-INF/views/them-tin.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-tin/sua")) {
                String id = request.getParameter("id");
                News n = newsDAO.findById(id);
                List<Category> cats = categoryDAO.findAll();
                request.setAttribute("n", n);
                request.setAttribute("cats", cats);
                request.getRequestDispatcher("/WEB-INF/views/sua-tin.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-tin/xoa")) {
                String id = request.getParameter("id");
                newsDAO.delete(id);
                response.sendRedirect(request.getContextPath()+"/quan-ly-tin");
            } else if (uri.endsWith("/chi-tiet")) {
                String id = request.getParameter("id");
                News n = newsDAO.findById(id);
                if (n!=null) {
                    // increase view count
                    n.setViewCount(n.getViewCount()+1);
                    newsDAO.update(n);
                }
                request.setAttribute("n", n);
                request.getRequestDispatcher("/WEB-INF/views/tin-chi-tiet.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        try {
            if (uri.endsWith("/quan-ly-tin/them")) {
                String id = request.getParameter("id").trim();
                String title = request.getParameter("title").trim();
                String content = request.getParameter("content").trim();
                String categoryId = request.getParameter("categoryId");
                String author = request.getParameter("author"); // in real app get from session
                Part imgPart = request.getPart("image");
                String uploadPath = request.getServletContext().getRealPath("/uploads");
                String filename = UploadUtils.saveImage(imgPart, uploadPath);
                News n = new News();
                n.setId(id); n.setTitle(title); n.setContent(content); n.setImage(filename); n.setPostedDate(new Date()); n.setAuthor(author); n.setViewCount(0); n.setCategoryId(categoryId); n.setHome(false);
                newsDAO.insert(n);

                // send newsletter to subscribers (configure SMTP parameters as needed)
                List<String> emails = newsletterDAO.getAllEmails();
                if (!emails.isEmpty()) {
                    String subject = "Bài viết mới: " + title;
                    String html = "<h2>" + title + "</h2><p>" + content + "</p><p><a href=\"" + request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()) + "/chi-tiet?id=" + id + "\">Xem chi tiết</a></p>";
                    // TODO: set smtpHost, smtpUser, smtpPass and port
                    String smtpHost = "smtp.gmail.com";
                    String smtpUser = "your-email@gmail.com";
                    String smtpPass = "your-app-password";
                    int smtpPort = 587;
                    boolean useTls = true;
                    EmailUtils.sendBulk(emails, subject, html, smtpHost, smtpUser, smtpPass, smtpPort, useTls);
                }

                response.sendRedirect(request.getContextPath()+"/quan-ly-tin");
            } else if (uri.endsWith("/quan-ly-tin/sua")) {
                String id = request.getParameter("id").trim();
                News n = newsDAO.findById(id);
                n.setTitle(request.getParameter("title"));
                n.setContent(request.getParameter("content"));
                n.setCategoryId(request.getParameter("categoryId"));
                Part imgPart = request.getPart("image");
                String uploadPath = request.getServletContext().getRealPath("/uploads");
                String filename = UploadUtils.saveImage(imgPart, uploadPath);
                if (filename!=null) n.setImage(filename);
                newsDAO.update(n);
                response.sendRedirect(request.getContextPath()+"/quan-ly-tin");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
