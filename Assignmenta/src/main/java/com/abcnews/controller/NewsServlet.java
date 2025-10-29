package com.abcnews.controller;

import com.abcnews.dao.CategoryDAO;
import com.abcnews.dao.NewsDAO;
import com.abcnews.dao.NewsletterDAO;
import com.abcnews.model.News;
import com.abcnews.model.Category;
import com.abcnews.utils.EmailUtils;
import com.abcnews.utils.UploadUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@MultipartConfig
@WebServlet(name="NewsServlet", urlPatterns = {"/quan-ly-tin", "/quan-ly-tin/them", "/quan-ly-tin/sua", "/quan-ly-tin/xoa",})
public class NewsServlet extends HttpServlet {
    private final NewsDAO newsDAO = new NewsDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final NewsletterDAO newsletterDAO = new NewsletterDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        try {
            if (uri.endsWith("/quan-ly-tin")) {
                List<News> list = newsDAO.findAll();
                request.setAttribute("list", list);
                request.getRequestDispatcher("/views/admin/admin_news.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-tin/them")) {
                List<Category> cats = categoryDAO.findAll();
                request.setAttribute("cats", cats);
                request.getRequestDispatcher("/views/reporter/reporter_news_form.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-tin/sua")) {
                String id = request.getParameter("id");
                News n = newsDAO.findById(id);
                List<Category> cats = categoryDAO.findAll();
                request.setAttribute("n", n);
                request.setAttribute("cats", cats);
                request.getRequestDispatcher("/views/admin/admin_news.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-tin/xoa")) {
                String id = request.getParameter("id");
                newsDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/quan-ly-tin");
             
            }else {
                response.sendRedirect(request.getContextPath() + "/trang-chu");
            }
            

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // handle create/update/approve
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        try {
            if (uri.endsWith("/quan-ly-tin/them") || uri.endsWith("/quan-ly-tin/sua")) {
                boolean isNew = uri.endsWith("/quan-ly-tin/them");
                String id = request.getParameter("id");
                if (isNew) id = UUID.randomUUID().toString();

                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String categoryId = request.getParameter("categoryId");
                String author = request.getParameter("author");
                Part imgPart = request.getPart("image");
                String uploadPath = request.getServletContext().getRealPath("/uploads");
                String filename = UploadUtils.saveImage(imgPart, uploadPath);

                News n = isNew ? new News() : newsDAO.findById(id);
                if (n == null) n = new News();

                n.setId(id);
                n.setTitle(title);
                n.setContent(content);
                if (filename != null) n.setImage(filename);
                n.setAuthor(author != null ? author : "admin");
                n.setCategoryId(categoryId);
                n.setHome("on".equals(request.getParameter("home")));
                n.setApproved("on".equals(request.getParameter("approved")));
                n.setPostedDate(LocalDateTime.now());
                if (isNew) n.setViewCount(0);

                if (isNew) newsDAO.insert(n); else newsDAO.update(n);

                // send newsletter if requested
                if ("on".equals(request.getParameter("sendNewsletter"))) {
                    List<String> emails = newsletterDAO.getAllEmails();
                    if (!emails.isEmpty()) {
                        String subject = "Bài viết mới: " + title;
                        String html = "<h2>" + title + "</h2><p>" + content + "</p>" +
                                "<p><a href=\"" + request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()) + "/chi-tiet?id=" + id + "\">Xem chi tiết</a></p>";
                        // configure SMTP credentials here or use application settings
                        String smtpHost = "smtp.gmail.com";
                        String smtpUser = "your-email@gmail.com";
                        String smtpPass = "your-app-password";
                        int smtpPort = 587;
                        boolean useTls = true;
                        EmailUtils.sendBulk(emails, subject, html, smtpHost, smtpUser, smtpPass, smtpPort, useTls);
                    }
                }

                response.sendRedirect(request.getContextPath() + "/quan-ly-tin");
                return;
            } else if (uri.endsWith("/quan-ly-tin/duyet-bai")) {
                String id = request.getParameter("id");
                if (id != null && !id.isBlank()) {
                    newsDAO.approveNews(id, true);
                }
                response.sendRedirect(request.getContextPath() + "/quan-ly-tin");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/quan-ly-tin");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
