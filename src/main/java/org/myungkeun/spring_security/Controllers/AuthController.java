package org.myungkeun.spring_security.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.entities.User;
import org.myungkeun.spring_security.payload.AuthRequest;
import org.myungkeun.spring_security.payload.UserLoginRequest;
import org.myungkeun.spring_security.payload.UserLoginResponse;
import org.myungkeun.spring_security.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원가입 api
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.registerUser(authRequest), HttpStatus.CREATED);
    }

    //로그인 API
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loggedInUser(@RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(authService.loggedInUser(userLoginRequest));
    }

    @PostMapping("/refresh")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }
}
