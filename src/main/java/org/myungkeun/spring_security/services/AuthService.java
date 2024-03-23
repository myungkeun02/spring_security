package org.myungkeun.spring_security.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.entities.Role;
import org.myungkeun.spring_security.entities.Token;
import org.myungkeun.spring_security.entities.TokenType;
import org.myungkeun.spring_security.entities.User;
import org.myungkeun.spring_security.payload.*;
import org.myungkeun.spring_security.repositories.TokenRepository;
import org.myungkeun.spring_security.repositories.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;


    public String registerUser(AuthRequest authRequest) {
        var user = User.builder()
                .username(authRequest.getUsername())
                .email(authRequest.getEmail())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        MailRequest mailRequest = new MailRequest();
        mailRequest.setAddress(authRequest.getEmail());
        mailRequest.setSubject("Welcome myungkeun world");
        mailRequest.setText("환영합니다"+authRequest.getUsername()+"님");
        emailService.mailsend(mailRequest);
        return "user registered successfully";
    }

    public UserLoginResponse loggedInUser(UserLoginRequest userLoginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(),
                        userLoginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revorkAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return UserLoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revorkAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revorkAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = UserLoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    public UserInfoResponse getProfileInfoByToken(
            HttpServletRequest request,
            HttpServletResponse response
    )  {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String userEmail;
        final String accessToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("No user");
        }
        accessToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(accessToken);
        var user = this.userRepository.findByEmail(userEmail)
                .orElseThrow();
        var userInfoResponse = UserInfoResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
        return userInfoResponse;
    }

}
