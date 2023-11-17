package kpfu.itis.servlets;

import kpfu.itis.dto.SignInForm;
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
import java.io.IOException;

@WebServlet("/sign_in")
public class SignInServlet extends HttpServlet {
    private AuthorizationService authorizationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        this.authorizationService = (AuthorizationService) context.getAttribute("authorizationService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/resources/view/sign_in/sign_in.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            SignInForm form = SignInForm.builder()
                    .email(request.getParameter("email"))
                    .password(request.getParameter("password"))
                    .build();
            UserDto user = authorizationService.signIn(form);
            String email = user.getEmail();
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("email", email);

        } catch (SmException e) {
            request.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect("sign_in");


        }


        response.sendRedirect("profile");
    }
}

