package kpfu.itis.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/")
public class FirstTimeRedirectFilter implements Filter {


    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Boolean isRedirected = (Boolean) httpRequest.getSession().getAttribute("isRedirected");

        boolean alreadyRedirected = isRedirected != null && isRedirected.booleanValue();
        if (alreadyRedirected || httpRequest.getRequestURI().endsWith("/concert")) {
            chain.doFilter(request, response);
        } else {
            httpRequest.getSession().setAttribute("isRedirected", true);
            httpResponse.sendRedirect( "concert");
        }
    }

    @Override
    public void destroy() {
    }
}