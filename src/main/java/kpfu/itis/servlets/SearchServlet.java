package kpfu.itis.servlets;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.repositories.SingerRepository;
import kpfu.itis.services.ConcertSingerService;
import kpfu.itis.services.SearchService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.annotation.WebServlet;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private SearchService searchService;
    private ConcertSingerService concertSingerService;


    @Override
    public void init(ServletConfig config) {
        this.searchService = (SearchService) config.getServletContext().getAttribute("searchService");
        this.concertSingerService = (ConcertSingerService) config.getServletContext().getAttribute("concertSingerService");

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        Optional<ConcertForm> optionalConcert = searchService.search(searchTerm);
        Long id = searchService.getId(searchTerm);
        List<String> singerNames = concertSingerService.getSingerNickname(id);
        ConcertForm concertForm = optionalConcert.orElse(null);
        request.setAttribute("concertDetails", concertForm);
        request.setAttribute("singerNames", singerNames);


        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/resources/view/concert_details/concertDetails.jsp");
        dispatcher.forward(request, response);


    }
}
