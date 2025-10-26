
package com.abcnews.controller;

import com.abcnews.dao.NewsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name="ApproveNewsServlet", urlPatterns = {"/quan-ly-tin/duyet-bai"})
public class ApproveNewsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id==null){ response.sendRedirect(request.getContextPath()+"/quan-ly-tin"); return; }
        try {
            NewsDAO dao = new NewsDAO();
            dao.setStatus(id, "approved");
            response.sendRedirect(request.getContextPath()+"/quan-ly-tin");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
