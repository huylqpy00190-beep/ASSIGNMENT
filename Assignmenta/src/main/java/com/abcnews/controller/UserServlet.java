package com.abcnews.controller;

import com.abcnews.dao.UserDAO;
import com.abcnews.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/quan-ly-nguoi-dung", "/quan-ly-nguoi-dung/them", "/quan-ly-nguoi-dung/sua", "/quan-ly-nguoi-dung/xoa", "/dang-nhap", "/dang-xuat"})
public class UserServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        try {
            if (uri.endsWith("/quan-ly-nguoi-dung")) {
                List<User> list = dao.findAll();
                request.setAttribute("users", list);
                request.getRequestDispatcher("/views/admin/admin_users.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-nguoi-dung/them")) {
                request.getRequestDispatcher("/views/admin/admin_users.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-nguoi-dung/sua")) {
                String id = request.getParameter("id");
                User u = dao.findById(id);
                request.setAttribute("u", u);
                request.getRequestDispatcher("/views/admin/admin_users.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-nguoi-dung/xoa")) {
                dao.delete(request.getParameter("id"));
                response.sendRedirect(request.getContextPath() + "/quan-ly-nguoi-dung");
            } else if (uri.endsWith("/dang-xuat")) {
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/trang-chu");
            } else {
                request.getRequestDispatcher("/views/index/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        try {
            request.setCharacterEncoding("UTF-8");
            if (uri.endsWith("/quan-ly-nguoi-dung/them")) {
                User u = new User();
                u.setId(request.getParameter("id"));
                u.setPassword(request.getParameter("password"));
                u.setFullname(request.getParameter("fullname"));
                String b = request.getParameter("birthday");
                if (b != null && !b.isBlank()) u.setBirthday(LocalDate.parse(b));
                u.setMobile(request.getParameter("mobile"));
                u.setEmail(request.getParameter("email"));
                // role may be "1" or "on"
                String roleParam = request.getParameter("role");
                u.setRole("1".equals(roleParam) || "on".equals(roleParam) || "true".equalsIgnoreCase(roleParam));
                dao.insert(u);
                response.sendRedirect(request.getContextPath() + "/quan-ly-nguoi-dung");
            } else if (uri.endsWith("/quan-ly-nguoi-dung/sua")) {
                User u = new User();
                u.setId(request.getParameter("id"));
                u.setPassword(request.getParameter("password"));
                u.setFullname(request.getParameter("fullname"));
                String b = request.getParameter("birthday");
                if (b != null && !b.isBlank()) u.setBirthday(LocalDate.parse(b));
                u.setMobile(request.getParameter("mobile"));
                u.setEmail(request.getParameter("email"));
                String roleParam = request.getParameter("role");
                u.setRole("1".equals(roleParam) || "on".equals(roleParam) || "true".equalsIgnoreCase(roleParam));
                dao.update(u);
                response.sendRedirect(request.getContextPath() + "/quan-ly-nguoi-dung");
            } else if (uri.endsWith("/dang-nhap")) {
                // handled separately by LoginServlet; left for compatibility
                doPost(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
