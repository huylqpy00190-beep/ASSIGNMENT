package com.abcnews.controller;

import com.abcnews.dao.NewsletterDAO;
import com.abcnews.model.Newsletter;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/newsletters") // Đã điều chỉnh URL để khớp với JSP: /admin/newsletters
public class NewsletterController extends HttpServlet { 
    private static final long serialVersionUID = 1L;
    
    private NewsletterDAO newsletterDAO;

    @Override
    public void init() throws ServletException {
        this.newsletterDAO = new NewsletterDAO();
    }

    // --- Xử lý GET: Hiển thị trang hoặc Xóa (DELETE) ---
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            handleDelete(request, response);
        } else {
            // Mặc định: Hiển thị danh sách
            displayList(request, response);
        }
    }

    // --- Xử lý POST: Chuyển đổi trạng thái (TOGGLE) ---
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if ("toggle".equals(action)) {
            handleToggle(request, response);
        } else {
            // Xử lý các POST khác nếu có, hoặc chỉ hiển thị lại danh sách
            displayList(request, response); 
        }
    }
    
    // --- Phương thức con: Hiển thị danh sách ---
    private void displayList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Newsletter> newsletterList = newsletterDAO.findAll();
            // Đổi tên thuộc tính từ "newsletterList" sang "newsletters" để khớp với JSP
            request.setAttribute("newsletters", newsletterList); 
            
            String view = "/views/admin/admin_newsletters.jsp";
            request.getRequestDispatcher(view).forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi tải danh sách Newsletter: " + e.getMessage());
        }
    }

    // --- Phương thức con: Xử lý Xóa (DELETE) ---
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email != null && !email.trim().isEmpty()) {
            try {
                newsletterDAO.delete(email);
                request.getSession().setAttribute("message", "Đã xóa email: " + email);
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Lỗi khi xóa email: " + e.getMessage());
            }
        }
        // Redirect về trang danh sách sau khi xóa
        response.sendRedirect(request.getContextPath() + "/admin/newsletters");
    }

    // --- Phương thức con: Xử lý Chuyển đổi trạng thái (TOGGLE) ---
    private void handleToggle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email != null && !email.trim().isEmpty()) {
            try {
                newsletterDAO.toggleEnabled(email);
                request.getSession().setAttribute("message", "Đã chuyển đổi trạng thái của email: " + email);
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Lỗi khi chuyển đổi trạng thái: " + e.getMessage());
            }
        }
        // Redirect về trang danh sách sau khi thay đổi
        response.sendRedirect(request.getContextPath() + "/admin/newsletters");
    }
}