package org.myungkeun.spring_security.services;

import org.myungkeun.spring_security.payload.UpdatePasswordRequest;
import org.myungkeun.spring_security.payload.UserInfoResponse;

import java.security.Principal;

public interface UserService {
    String updatePassword(UpdatePasswordRequest updatePasswordRequest, Principal connectedUser);
    UserInfoResponse getProfileInfoByToken(Principal connectedUser);

}
