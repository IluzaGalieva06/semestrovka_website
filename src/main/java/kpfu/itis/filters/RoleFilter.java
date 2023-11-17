package kpfu.itis.filters;

import kpfu.itis.dto.UserDto;
import kpfu.itis.models.UserRole;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/add-concert")
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        if (user != null) {
            if (user.getRole() == UserRole.ADMIN) {
                chain.doFilter(request, response);
            } else if (user.getRole() == UserRole.USER) {
                httpResponse.sendRedirect("profile");
            }
        } else {
            httpResponse.sendRedirect("sign_up");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
