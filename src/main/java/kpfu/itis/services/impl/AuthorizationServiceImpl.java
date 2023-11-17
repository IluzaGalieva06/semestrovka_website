package kpfu.itis.services.impl;

import kpfu.itis.dto.SignInForm;
import kpfu.itis.dto.SignUpForm;
import kpfu.itis.dto.UserDto;
import kpfu.itis.exceptions.SmException;
import kpfu.itis.models.User;
import kpfu.itis.models.UserRole;
import kpfu.itis.repositories.UserRepository;
import kpfu.itis.services.AuthorizationService;
import kpfu.itis.services.PasswordEncoder;
import kpfu.itis.services.UserMapper;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private UserRepository usersRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDto signUp(SignUpForm form) throws SmException {
        if (form.getEmail() == null) {
            throw new SmException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if (optionalUser.isPresent()) {
            throw new SmException("User with email " + form.getEmail() + " already exist");
        }
        UserRole userRole = determineUserRole(form.getEmail(), form.getPassword());
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        User user = userMapper.toUser(form);
        user.setRole(userRole);
        User savedUser = usersRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto signIn(SignInForm form) throws SmException {
        if(form.getEmail() == null) {
            throw new SmException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if(optionalUser.isEmpty()) {
            throw new SmException("User with email " + form.getEmail() + " not found.");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            throw new SmException("Wrong password");
        }
        UserRole userRole = determineUserRole(form.getEmail(), form.getPassword());

        UserDto userDto = userMapper.toDto(user);
        userDto.setRole(userRole);

        return userDto;
    }
    private UserRole determineUserRole(String email, String password) {
        if ("admin123@admin.ru".equals(email) && "1231231234".equals(password) ) {
            return UserRole.ADMIN;
        } else {
            return UserRole.USER;
        }
    }
}
