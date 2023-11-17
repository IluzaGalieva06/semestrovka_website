package kpfu.itis.listeners;

import kpfu.itis.repositories.*;
import kpfu.itis.repositories.impl.*;
import kpfu.itis.services.*;
import kpfu.itis.services.impl.*;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class InitListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "iluza06042004";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/semestrovka1";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String IMAGES_STORAGE_PATH = (String) "C:\\Users\\Илюза\\Desktop\\images\\";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME,
                    DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        UserMapper userMapper = new UserMapperImpl();
        UserRepository userRepository = new UserRepositoryImpl(connection);
        ConcertRepository concertRepository = new ConcertRepositoryImpl(connection);


        AuthorizationService authorizationService = new AuthorizationServiceImpl(userRepository, userMapper, passwordEncoder);
        FilesRepository filesRepository = new FilesRepositoryImpl(connection);
        TicketRepository ticketRepository = new TicketRepositoryImpl(connection);
        SingerRepository singerRepository = new SingerRepositoryImpl(connection);
        ConcertSingerRepository concertSingerRepository = new ConcertSingerRepositoryImpl(connection);


        FilesService filesService = new FilesServiceImpl(IMAGES_STORAGE_PATH, filesRepository, userRepository, concertRepository);
        TicketService ticketService = new TicketServiceImpl(ticketRepository);
        ConcertService concertService = new ConcertServiceImpl(concertRepository);
        SearchService searchService = new SearchServiceImpl(concertRepository);
        UserService userService = new UserServiceImpl(userRepository, ticketRepository);
        ConcertSingerService concertSingerService = new ConcertSingerServiceImpl(concertSingerRepository, singerRepository, concertRepository);
        DeleteService deleteService = new DeleteServiceImpl(concertSingerRepository, ticketRepository, concertRepository);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("userRepository", userRepository);
        servletContext.setAttribute("concertRepository", concertRepository);
        servletContext.setAttribute("singerRepository", singerRepository);
        servletContext.setAttribute("concertSingerRepository", concertSingerRepository);


        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("concertSingerService", concertSingerService);


        servletContext.setAttribute("filesService", filesService);
        servletContext.setAttribute("concertService", concertService);
        servletContext.setAttribute("searchService", searchService);
        servletContext.setAttribute("ticketService", ticketService);
        servletContext.setAttribute("deleteService", deleteService);


        servletContext.setAttribute("authorizationService", authorizationService);


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

