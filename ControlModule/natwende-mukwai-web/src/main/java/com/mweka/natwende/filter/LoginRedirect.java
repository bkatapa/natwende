package com.mweka.natwende.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;

@WebFilter(urlPatterns = {"/*/j_security_check", "/j_security_check"})
public class LoginRedirect implements Filter {

    @Inject
    protected transient Log log;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/home.xhtml");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }
}
