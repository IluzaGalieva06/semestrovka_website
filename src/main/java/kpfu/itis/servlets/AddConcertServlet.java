package kpfu.itis.servlets;

import kpfu.itis.dto.ConcertForm;
import kpfu.itis.models.ConcertSinger;
import kpfu.itis.models.FileInfo;
import kpfu.itis.models.Singer;
import kpfu.itis.services.ConcertService;
import kpfu.itis.services.ConcertSingerService;
import kpfu.itis.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/add-concert")
@MultipartConfig
public class AddConcertServlet extends HttpServlet {

    private ConcertService concertService;
    private FilesService filesService;
    private ConcertSingerService concertSingerService;

    @Override
    public void init(ServletConfig config) {
        this.concertService = (ConcertService) config.getServletContext().getAttribute("concertService");
        this.filesService = (FilesService) config.getServletContext().getAttribute("filesService");
        this.concertSingerService = (ConcertSingerService) config.getServletContext().getAttribute("concertSingerService");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/resources/view/add_concert/addConcert.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("poster");
        ConcertForm concertForm = ConcertForm.builder()
                .name(request.getParameter("name"))
                .location(request.getParameter("location"))
                .date(Date.valueOf(request.getParameter("date")))
                .description(request.getParameter("description"))
                .price(Long.valueOf(request.getParameter("price")))
                .build();

        FileInfo fileInfo = filesService.saveFileToStoragePoster(
                concertForm,
                part.getInputStream(),
                part.getName(),
                part.getContentType(),
                part.getSize());

        concertForm.setPosterId(fileInfo.getId());
        concertService.addConcert(concertForm);
        String singerNamesString = request.getParameter("singers");
        String[] singerNames = singerNamesString.split(", ");

        for (String artistName : singerNames) {
            Singer artist = concertSingerService.getSingerByName(artistName);

            if (artist != null) {

                ConcertSinger concertSinger = new ConcertSinger(concertService.getIdByName(request.getParameter("name")), artist.getId());
                concertSingerService.save(concertSinger);
            }
        }

        response.sendRedirect("add-concert");
        HttpSession session = request.getSession(true);
        session.setAttribute("concertForm", concertForm);
    }
}
