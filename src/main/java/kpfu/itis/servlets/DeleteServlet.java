package kpfu.itis.servlets;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.services.ConcertSingerService;
import kpfu.itis.services.DeleteService;
import kpfu.itis.services.TicketService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    private DeleteService deleteService;
    private ConcertSingerService concertSingerService;


    @Override
    public void init(ServletConfig config) {
        this.deleteService = (DeleteService) config.getServletContext().getAttribute("deleteService");
        this.concertSingerService = (ConcertSingerService) config.getServletContext().getAttribute("concertSingerService");

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String concertId = request.getParameter("concertId");
        Long id = Long.parseLong(concertId);

        deleteService.deleteTicket(id);
        deleteService.deleteConcertSinger(id);
        deleteService.deleteConcert(id);
        List<ConcertForm> concerts = concertSingerService.getConcertForm();
        Map<Long, List<String>> concertSingerMap = concertSingerService.getAllSingerFromConcertForm();

        request.setAttribute("concertsForJsp", concerts);
        request.setAttribute("concertSingerMap", concertSingerMap);
        request.getRequestDispatcher("WEB-INF/resources/view/main/main.jsp").forward(request, response);
    }
}
