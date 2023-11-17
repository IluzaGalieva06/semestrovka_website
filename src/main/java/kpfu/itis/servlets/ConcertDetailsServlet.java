package kpfu.itis.servlets;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.services.ConcertSingerService;
import kpfu.itis.services.SearchService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/concertDetails")
public class ConcertDetailsServlet extends HttpServlet {
    private SearchService searchService;
    private ConcertSingerService concertSingerService;

    @Override
    public void init(ServletConfig config) {
        this.searchService = (SearchService) config.getServletContext().getAttribute("searchService");
        this.concertSingerService = (ConcertSingerService) config.getServletContext().getAttribute("concertSingerService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String concertId = request.getParameter("id");
        Long id = Long.parseLong(concertId);
        List<String> singerNames = concertSingerService.getSingerNickname(id);

        Optional<ConcertForm> concertOptional = searchService.searchId(id);
        ConcertForm concertForm = concertOptional.orElse(null);
        request.setAttribute("concertDetails", concertForm);
        request.setAttribute("singerNames", singerNames);

        request.getRequestDispatcher("resources/view/html/concertDetails.jsp").forward(request, response);
    }
}
