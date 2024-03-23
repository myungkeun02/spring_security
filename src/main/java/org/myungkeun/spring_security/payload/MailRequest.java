package org.myungkeun.spring_security.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
    private String address;
    private String subject;
    private String text;
}
