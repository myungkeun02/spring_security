package org.myungkeun.spring_security.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
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
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.getProfileInfoByToken(request, response));
    }

    @PatchMapping("/change/password")
    public ResponseEntity<String> updatePassword(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody UpdatePasswordRequest passwordRequest
    ) {
        return ResponseEntity.ok(userService.updatePassword(passwordRequest, request, response));
    }
}
