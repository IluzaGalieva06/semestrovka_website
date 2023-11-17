package kpfu.itis.servlets;


import kpfu.itis.dto.ConcertForm;
import kpfu.itis.services.ConcertSingerService;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@WebServlet("/concert")
public class MainServlet extends HttpServlet {

    private ConcertSingerService concertSingerService;

    @Override
    public void init(ServletConfig config) {
        this.concertSingerService = (ConcertSingerService) config.getServletContext().getAttribute("concertSingerService");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ConcertForm> concerts = concertSingerService.getConcertForm();
        Map<Long, List<String>> concertSingerMap = concertSingerService.getAllSingerFromConcertForm();

        request.setAttribute("concertsForJsp", concerts);
        request.setAttribute("concertSingerMap", concertSingerMap);
        request.getRequestDispatcher("WEB-INF/resources/view/main/main.jsp").forward(request, response);

    }
}
