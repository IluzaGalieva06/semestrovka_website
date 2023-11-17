package kpfu.itis.servlets;

import kpfu.itis.dto.TicketForm;
import kpfu.itis.services.TicketService;
import kpfu.itis.utils.EmailSender;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/processPurchase")
public class PurchaseServlet extends HttpServlet {
    private TicketService ticketService;

    @Override
    public void init(ServletConfig config) {
        this.ticketService = (TicketService) config.getServletContext().getAttribute("ticketService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/resources/view/buy_ticket/buyTicket.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String concertPrice = request.getParameter("concertPrice");
        String concertName = request.getParameter("concertName");
        HttpSession session = request.getSession(false);
        String userEmail = (String) session.getAttribute("email");

        if (concertPrice != null && !concertPrice.isEmpty()) {
            try {
                Long price = Long.parseLong(concertPrice);
                TicketForm ticket = TicketForm.builder()
                        .concertName(concertName)
                        .userEmail(userEmail)
                        .price(price)
                        .build();

                ticketService.addTicketToConcert(ticket);
                System.out.println(userEmail);

                EmailSender emailSender = new EmailSender(userEmail, "New Purchase Notification", "Вы успешно купили билет!");

                Thread emailThread = new Thread(emailSender);
                emailThread.start();

                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/resources/view/success/success.jsp");
                dispatcher.forward(request, response);
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        }
    }
}