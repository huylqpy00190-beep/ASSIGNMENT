package com.abcnews.controller;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.abcnews.utils.DBContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/dang-nhap"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String password = request.getParameter("password");

        try (Connection conn = DBContext.getConnection()) {
            String sql = "SELECT * FROM Users WHERE Id=? AND Password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                java.util.Map<String, Object> userMap = new java.util.HashMap<>();
                userMap.put("fullname", rs.getString("Fullname"));
                userMap.put("role", rs.getBoolean("Role"));
                session.setAttribute("user", userMap);  // ✅ header.jsp đọc được

                if (rs.getBoolean("Role")) {
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

