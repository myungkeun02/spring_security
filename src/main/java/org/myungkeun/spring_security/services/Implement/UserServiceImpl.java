package org.myungkeun.spring_security.services.Implement;

import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_security.entities.User;
import org.myungkeun.spring_security.payload.UpdatePasswordRequest;
import org.myungkeun.spring_security.payload.UserInfoResponse;
import org.myungkeun.spring_security.repositories.UserRepository;
import org.myungkeun.spring_security.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public String updatePassword(
            UpdatePasswordRequest updatePasswordRequest,
            Principal connectedUser
    ) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(updatePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmPassword())) {
            throw new IllegalStateException("Password do not match");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return "updated password";
    }
    public UserInfoResponse getProfileInfoByToken(
            Principal connectedUser
    )  {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var userInfoResponse = UserInfoResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
        return userInfoResponse;
    }
}
