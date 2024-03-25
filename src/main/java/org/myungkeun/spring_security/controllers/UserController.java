package org.myungkeun.spring_security.controllers;

import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.payload.UpdatePasswordRequest;
import org.myungkeun.spring_security.payload.UserInfoResponse;
import org.myungkeun.spring_security.services.Implement.UserServiceImpl;
import org.myungkeun.spring_security.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
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
