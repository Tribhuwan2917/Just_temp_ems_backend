package com.employee_management_backend_Application.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MessageSenderExceptionHandler {
    @ExceptionHandler(MessageSenderException.class)
    public String messageSenderExceptionHandler(MessageSenderException emailSenderException)
    {
        return emailSenderException.getMessage();
    }
}
