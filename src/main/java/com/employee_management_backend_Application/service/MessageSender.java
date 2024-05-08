package com.employee_management_backend_Application.service;

import java.io.IOException;

public interface MessageSender {
    public String sendSMS(SMSSenderRequest smsSenderRequest) throws IOException;
    public String sendEmail(EmailSenderRequest emailSenderRequest) throws IOException;
}
