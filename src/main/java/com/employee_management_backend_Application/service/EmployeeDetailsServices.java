package com.employee_management_backend_Application.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public interface EmployeeDetailsServices {
    public List<EmployeeDetailsResponse> getAllEmployeeDetails();
    public EmployeeDetailsResponse getEmployeeDeatils(Integer employeeDetailsId);
    public Integer postEmployeeDetails(EmployeeDetailsRequest employeeDetailsRequest)throws IOException;
    public Integer deleteEmployeeDetails(Integer employeeDetailsId);
    public Integer updateEmployeedetails(EmployeeDetailsRequest employeeDetailsRequest);
}
