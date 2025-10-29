package com.abcnews.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/dang-xuat"})
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession s = request.getSession(false);
        if (s != null) s.invalidate();
        response.sendRedirect(request.getContextPath() + "/trang-chu");
    }
}
