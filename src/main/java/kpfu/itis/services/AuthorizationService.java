package kpfu.itis.services;

import kpfu.itis.dto.SignInForm;
import kpfu.itis.dto.SignUpForm;
import kpfu.itis.dto.UserDto;

public interface AuthorizationService {
    UserDto signUp(SignUpForm form);
    UserDto signIn(SignInForm form);
}
