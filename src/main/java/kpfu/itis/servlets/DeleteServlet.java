package kpfu.itis.servlets;

import kpfu.itis.services.DeleteService;
import kpfu.itis.services.TicketService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private DeleteService deleteService;

    @Override
    public void init(ServletConfig config) {
        this.deleteService = (DeleteService) config.getServletContext().getAttribute("deleteService");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String concertId = request.getParameter("concertId");
        Long id = Long.parseLong(concertId);

        deleteService.deleteTicket(id);
        deleteService.deleteConcertSinger(id);
        deleteService.deleteConcert(id);
        request.getRequestDispatcher("resources/view/html/main.jsp").forward(request, response);
    }
}
