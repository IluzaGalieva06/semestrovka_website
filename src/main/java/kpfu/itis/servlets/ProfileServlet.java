package kpfu.itis.servlets;

import kpfu.itis.dto.TicketForm;

import kpfu.itis.services.TicketService;
import kpfu.itis.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private TicketService ticketService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        this.ticketService = (TicketService) config.getServletContext().getAttribute("ticketService");
        this.userService = (UserService) config.getServletContext().getAttribute("userService");

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");
        Long id = userService.getUserId(userEmail);
        List<TicketForm> ticketForms = ticketService.getTicketDetails(id);

        Object user = request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        request.setAttribute("ticketForms", ticketForms);
        request.getRequestDispatcher("resources/view/html/profile.jsp").forward(request, response);

    }
}



