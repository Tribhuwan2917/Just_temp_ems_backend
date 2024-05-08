package com.employee_management_backend_Application.service;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SMSSenderRequest {
    private String employeeMobileNo;
    private String message;

}
