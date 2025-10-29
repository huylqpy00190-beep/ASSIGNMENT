package com.abcnews.controller;

import com.abcnews.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name="ApproveReporterServlet", urlPatterns = {"/quan-ly-nguoi-dung/duyet-phong-vien"})
public class ApproveReporterServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Map<String,Object>> pending = dao.getPendingReporterRequests();
            request.setAttribute("pendingList", pending);
            request.getRequestDispatcher("/views/admin/approve_reporters.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email == null || email.isBlank()) {
            response.sendRedirect(request.getContextPath()+"/quan-ly-nguoi-dung");
            return;
        }
        try {
            dao.approveReporterByEmail(email);
            response.sendRedirect(request.getContextPath()+"/quan-ly-nguoi-dung");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
