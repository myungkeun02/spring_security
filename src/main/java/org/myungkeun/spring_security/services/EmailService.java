package org.myungkeun.spring_security.services;

import org.myungkeun.spring_security.payload.MailRequest;

public interface EmailService {
    void mailSend(MailRequest request);
}
