package com.abcnews.filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter("/*")
public class I18nFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if(request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            String lang = req.getParameter("lang");
            if(lang != null) {
                req.getSession().setAttribute("lang", lang);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() { }
}