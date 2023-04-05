package com.capstone.server.Auth;

import com.capstone.server.Etc.JsonRequestWrapper;
import com.capstone.server.Exception.ExceptionEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CustomFilter implements Filter {
    public CustomFilter(){

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            JsonRequestWrapper jrw = new JsonRequestWrapper((HttpServletRequest) request);
            chain.doFilter(jrw, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("exception", ExceptionEnum.RUNTIME_EXCEPTION.getMessage());
        }
    }
}
