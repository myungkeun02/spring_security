package org.myungkeun.spring_security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.payload.UpdatePasswordRequest;
import org.myungkeun.spring_security.payload.UserInfoResponse;
import org.myungkeun.spring_security.services.AuthService;
import org.myungkeun.spring_security.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor // 이거 없으면 constructor 처리 해줘야함
public class UserController {
    private final AuthService authService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserInfoResponse> getUserInfoByToken(
        Principal connectedUser
    ) {
        return ResponseEntity.ok(userService.getProfileInfoByToken(connectedUser));
    }

    @PatchMapping("/change/password")
    public ResponseEntity<String> updatePassword(
            Principal connectedUser,
            @RequestBody UpdatePasswordRequest request
    ) {

        return ResponseEntity.ok(userService.updatePassword(request, connectedUser));
    }
}
