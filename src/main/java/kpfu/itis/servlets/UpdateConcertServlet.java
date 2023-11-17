package kpfu.itis.servlets;

import kpfu.itis.services.ConcertService;
import kpfu.itis.services.ConcertSingerService;
import kpfu.itis.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/update-concert")
public class UpdateConcertServlet extends HttpServlet {
    private ConcertService concertService;

    @Override
    public void init(ServletConfig config) {
        this.concertService = (ConcertService) config.getServletContext().getAttribute("concertService");


    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("resources/view/html/update-concert.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String concertId = request.getParameter("concertId");
        Long id = Long.parseLong(concertId);
        String concertName = request.getParameter("concertName");
        String concertDate = request.getParameter("concertDate");
        concertService.update(id, Date.valueOf(concertDate), concertName);
        response.sendRedirect("concert");



    }
}
