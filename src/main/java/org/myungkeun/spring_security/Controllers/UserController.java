package org.myungkeun.spring_security.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.payload.UserInfoResponse;
import org.myungkeun.spring_security.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor // 이거 없으면 constructor 처리 해줘야함
public class UserController {
    private final AuthService authService;
    @GetMapping()
    public ResponseEntity<UserInfoResponse> getUserInfoByToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.getProfileInfoByToken(request, response));

    }
}
