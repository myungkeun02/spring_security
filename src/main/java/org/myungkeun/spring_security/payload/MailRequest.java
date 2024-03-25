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


// blog 만드는데.. 어떤 요소가 필요하더라 (유저 아이디가 연동될수있게 만들라그랬음)