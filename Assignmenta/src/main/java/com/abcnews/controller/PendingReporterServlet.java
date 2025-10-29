package com.abcnews.controller;

import com.abcnews.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name="PendingReporterServlet", urlPatterns = {"/dang-ky-phong-vien"})
public class PendingReporterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            if (email == null || email.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/views/index/index.jsp?msg=missing");
                return;
            }
            UserDAO dao = new UserDAO();
            dao.requestReporter(fullname, email);
            response.sendRedirect(request.getContextPath() + "/views/index/index.jsp?msg=requested");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
