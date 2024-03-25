package org.myungkeun.spring_security.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myungkeun.spring_security.entities.Role;
import org.myungkeun.spring_security.entities.User;
import org.myungkeun.spring_security.payload.AuthRequest;
import org.myungkeun.spring_security.payload.MailRequest;
import org.myungkeun.spring_security.payload.UserLoginRequest;
import org.myungkeun.spring_security.payload.UserLoginResponse;

import java.io.IOException;

public interface AuthService {
    String registerUser(AuthRequest authRequest);

    UserLoginResponse loggedInUser(UserLoginRequest userLoginRequest);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
