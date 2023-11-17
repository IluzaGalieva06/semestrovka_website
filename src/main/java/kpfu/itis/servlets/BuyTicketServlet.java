package kpfu.itis.servlets;

import kpfu.itis.services.TicketService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/buy-ticket")
public class BuyTicketServlet extends HttpServlet {

    private TicketService ticketService;

    @Override
    public void init(ServletConfig config) {
        this.ticketService = (TicketService) config.getServletContext().getAttribute("ticketService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String price = request.getParameter("price");
        String name = request.getParameter("name");



        request.setAttribute("concertName", name);
        request.setAttribute("concertPrice", price);

        request.getRequestDispatcher("WEB-INF/resources/view/buy_ticket/buyTicket.jsp").forward(request, response);

    }

}
