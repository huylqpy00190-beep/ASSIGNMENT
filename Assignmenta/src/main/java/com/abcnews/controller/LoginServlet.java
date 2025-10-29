package com.abcnews.controller;

import com.abcnews.dao.UserDAO;
import com.abcnews.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/dang-nhap"})
public class LoginServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/views/index/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String id = request.getParameter("id");
            String password = request.getParameter("password");
            User u = dao.login(id, password);
            if (u != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", u); // store User object
                if (u.isRole()) {
                    response.sendRedirect(request.getContextPath() + "/views/admin/admin_dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/views/reporter/reporter_dashboard.jsp");
                }
            } else {
                request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
                request.getRequestDispatcher("/views/index/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
