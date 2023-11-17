package kpfu.itis.services.impl;


import kpfu.itis.models.User;
import kpfu.itis.repositories.TicketRepository;
import kpfu.itis.repositories.UserRepository;
import kpfu.itis.services.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private TicketRepository ticketRepository;

    public UserServiceImpl(UserRepository userRepository, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Long getUserId(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        Long id = user.get().getId();
        return id;
    }
}
