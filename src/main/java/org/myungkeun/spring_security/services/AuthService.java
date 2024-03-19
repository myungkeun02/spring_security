package org.myungkeun.spring_security.services;

import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.entities.Role;
import org.myungkeun.spring_security.entities.User;
import org.myungkeun.spring_security.payload.AuthRequest;
import org.myungkeun.spring_security.payload.UserLoginRequest;
import org.myungkeun.spring_security.payload.UserLoginResponse;
import org.myungkeun.spring_security.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String registerUser(AuthRequest authRequest) {
        var user = User.builder()
                .username(authRequest.getUsername())
                .email(authRequest.getEmail())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
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
        return UserLoginResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
