package org.myungkeun.spring_security.Controllers;

import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.entities.User;
import org.myungkeun.spring_security.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor // 이거 없으면 constructor 처리 해줘야함
public class UserController {
    private final AuthService authService;
    @GetMapping()
    public ResponseEntity<User> getUserInfoByToken(Principal connectedUser) {
        System.out.println(connectedUser);
        return ResponseEntity.ok(authService.getUseInfoByToken(connectedUser));
    }
}
