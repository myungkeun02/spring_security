package org.myungkeun.spring_security.services;

import lombok.AllArgsConstructor;
import org.myungkeun.spring_security.payload.MailRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender sender;
    private static final String FROM_ADDRESS = "audrms@gmail.com";

    public void mailsend(MailRequest request) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(request.getAddress());
        msg.setSubject(request.getSubject());
        msg.setText(request.getText());
        sender.send(msg);
    }
}
