package com.employee_management_backend_Application.exception;

public class MessageSenderException extends RuntimeException{
    public MessageSenderException(String message)
    {
        super(message);
    }
}
