package com.employee_management_backend_Application.service;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailSenderRequest {
    private String senderEmailId;
    private String receiverEmailId;
    private String emailMessage;
    private String emailSubject;
    private String emailAttachment;
}
