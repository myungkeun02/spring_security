package org.myungkeun.spring_security.Controllers;

import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.payload.AuthRequest;
import org.myungkeun.spring_security.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
