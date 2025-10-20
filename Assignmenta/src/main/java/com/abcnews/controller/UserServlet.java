package com.abcnews.controller;

import com.abcnews.dao.UserDAO;
import com.abcnews.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name="UserServlet", urlPatterns = {"/quan-ly-nguoi-dung", "/quan-ly-nguoi-dung/them", "/quan-ly-nguoi-dung/sua", "/quan-ly-nguoi-dung/xoa", "/dang-nhap", "/dang-xuat"})
public class UserServlet extends HttpServlet {
    private UserDAO dao = new UserDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        try {
            if (uri.endsWith("/quan-ly-nguoi-dung")) {
                List<User> list = dao.findAll();
                request.setAttribute("list", list);
                request.getRequestDispatcher("/WEB-INF/views/quan-ly-nguoi-dung.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-nguoi-dung/them")) {
                request.getRequestDispatcher("/WEB-INF/views/them-nguoi-dung.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-nguoi-dung/sua")) {
                String id = request.getParameter("id");
                User u = dao.findById(id);
                request.setAttribute("u", u);
                request.getRequestDispatcher("/WEB-INF/views/sua-nguoi-dung.jsp").forward(request, response);
            } else if (uri.endsWith("/quan-ly-nguoi-dung/xoa")) {
                dao.delete(request.getParameter("id"));
                response.sendRedirect(request.getContextPath()+"/quan-ly-nguoi-dung");
            } else if (uri.endsWith("/dang-xuat")) {
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath()+"/trang-chu");
            } else {
                request.getRequestDispatcher("/WEB-INF/views/dang-nhap.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        try {
            if (uri.endsWith("/dang-nhap")) {
                String id = request.getParameter("id"); String pw = request.getParameter("password");
                User u = dao.login(id, pw);
                if (u!=null) {
                    request.getSession().setAttribute("currentUser", u);
                    response.sendRedirect(request.getContextPath()+"/trang-chu");
                } else {
                    request.setAttribute("error", "Sai tên hoặc mật khẩu");
                    request.getRequestDispatcher("/WEB-INF/views/dang-nhap.jsp").forward(request, response);
                }
            } else if (uri.endsWith("/quan-ly-nguoi-dung/them")) {
                User u = new User();
                u.setId(request.getParameter("id"));
                u.setPassword(request.getParameter("password"));
                u.setFullname(request.getParameter("fullname"));
                // skipping birthday parse for brevity
                u.setMobile(request.getParameter("mobile"));
                u.setEmail(request.getParameter("email"));
                u.setRole("on".equals(request.getParameter("role")));
                dao.insert(u);
                response.sendRedirect(request.getContextPath()+"/quan-ly-nguoi-dung");
            } else if (uri.endsWith("/quan-ly-nguoi-dung/sua")) {
                User u = new User();
                u.setId(request.getParameter("id"));
                u.setPassword(request.getParameter("password"));
                u.setFullname(request.getParameter("fullname"));
                u.setMobile(request.getParameter("mobile"));
                u.setEmail(request.getParameter("email"));
                u.setRole("on".equals(request.getParameter("role")));
                dao.update(u);
                response.sendRedirect(request.getContextPath()+"/quan-ly-nguoi-dung");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
