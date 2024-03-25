package org.myungkeun.spring_security.services.Implement;

import lombok.AllArgsConstructor;
import org.myungkeun.spring_security.payload.MailRequest;
import org.myungkeun.spring_security.services.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private JavaMailSender sender;
    private static final String FROM_ADDRESS = "audrms@gmail.com";

    public void mailSend(MailRequest request) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(request.getAddress());
        msg.setSubject(request.getSubject());
        msg.setText(request.getText());
        sender.send(msg);
    }
}
