package com.employee_management_backend_Application.controller;

import com.employee_management_backend_Application.service.MessageSender;
import com.employee_management_backend_Application.service.EmailSenderRequest;
import com.employee_management_backend_Application.service.SMSSenderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("${employeeManagement.base.URL}")
@CrossOrigin
public class MessgeSenderController {
    @Autowired
    private MessageSender messageSender;
    @PostMapping("${employeeManagement.message.sendEmail}")
    public ResponseEntity<String> sendEmail(@RequestBody EmailSenderRequest emailSenderRequest) throws IOException
    {
        return new ResponseEntity<>(messageSender.sendEmail(emailSenderRequest), HttpStatus.OK);
    }
    @PostMapping("${employeeManagement.message.sendSMS}")
    public ResponseEntity<String> sendSMS(@RequestBody SMSSenderRequest smsSenderRequest) throws IOException
    {
        return new ResponseEntity<>(messageSender.sendSMS(smsSenderRequest),HttpStatus.OK);
    }
}
