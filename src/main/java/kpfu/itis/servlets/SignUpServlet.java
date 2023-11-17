package kpfu.itis.servlets;

import kpfu.itis.dto.SignUpForm;
import kpfu.itis.dto.UserDto;
import kpfu.itis.exceptions.SmException;
import kpfu.itis.services.AuthorizationService;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sign_up")
public class SignUpServlet extends HttpServlet {
    private AuthorizationService authorizationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.authorizationService = (AuthorizationService) servletContext.getAttribute("authorizationService");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/resources/view/sign_up/sign_up.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        SignUpForm form = SignUpForm.builder()
                .surname(request.getParameter("surname"))
                .username(request.getParameter("username"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();

        UserDto user;
        String email;
        try {
            user = authorizationService.signUp(form);
            email = user.getEmail();
        } catch (SmException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setAttribute("email", email);

        response.sendRedirect("profile");
    }


}




