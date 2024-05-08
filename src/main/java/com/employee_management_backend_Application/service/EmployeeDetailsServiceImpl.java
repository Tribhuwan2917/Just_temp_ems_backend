package com.employee_management_backend_Application.service;

import com.employee_management_backend_Application.entity.*;
import com.employee_management_backend_Application.exception.EmployeeDetailsAlreadyExistsException;
import com.employee_management_backend_Application.exception.EmployeeDetailsNotFoundException;
import com.employee_management_backend_Application.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@Transactional
public class EmployeeDetailsServiceImpl implements EmployeeDetailsServices {
    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;
    @Autowired
    private CurrentProjectRepository currentProjectRepository;
    @Autowired
    private PriviousProjectRepository priviousProjectRepository;
    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;
    @Autowired
    private EmployeeAttendanceRepository employeeAttendanceRepository;
    @Override
    public List<EmployeeDetailsResponse> getAllEmployeeDetails() {
        List<EmployeeDetails> employeeDetailsList=employeeDetailsRepository.findAll();
        List<EmployeeDetailsResponse> employeeDetailsResponseList=new ArrayList<EmployeeDetailsResponse>();
        if(employeeDetailsList.isEmpty())
        {
            throw new EmployeeDetailsNotFoundException("No Employee Exists");
        }
        else {

            for (EmployeeDetails employeeDetails:employeeDetailsList)
            {
                EmployeeDetailsResponse employeeDetailsResponse=new EmployeeDetailsResponse();
                Optional<CurrentProject> optionalCurrentProject=currentProjectRepository.getCurrentProjectByEmployeeId(employeeDetails.getEmployeeId());
                if(optionalCurrentProject.isPresent())
                employeeDetailsResponse.setEmployeeCurrentProjectId(optionalCurrentProject.get().getCurrentProjectId());
                List<Integer> employeePriviousProjectIdList=new ArrayList<>();
                List<PriviousProject> priviousProjectList=priviousProjectRepository.findPriviousProjectByEmployeeId(employeeDetails.getEmployeeId());
                for (PriviousProject priviousProject:priviousProjectList)
                {
                    employeePriviousProjectIdList.add(priviousProject.getPriviousProjectId());
                }
                employeeDetailsResponse.setEmployeePriviousProjectIds(employeePriviousProjectIdList);
                employeeDetailsResponse.setEmployeeEmail(employeeDetails.getEmployeeEmail());
                employeeDetailsResponse.setEmployeeCountry(employeeDetails.getEmployeeCountry());
                employeeDetailsResponse.setEmployeeLastName(employeeDetails.getEmployeeLastName());
                employeeDetailsResponse.setEmployeeFirstName(employeeDetails.getEmployeeFirstName());
                employeeDetailsResponse.setEmployeeId(employeeDetails.getEmployeeId());
                employeeDetailsResponse.setEmployeeAddressCity(employeeDetails.getEmployeeAddressCity());
                employeeDetailsResponse.setEmployeeDesignation(employeeDetails.getEmployeeDesignation());
                employeeDetailsResponse.setEmployeeSalaryPerMonth(employeeDetails.getEmployeeSalaryPerMonth());
                employeeDetailsResponse.setEmployeeGender(employeeDetails.getEmployeeGender());
                employeeDetailsResponse.setEmployeeImageUrl(employeeDetails.getEmployeeImageUrl());
                employeeDetailsResponse.setEmployeeMobileNo(employeeDetails.getEmployeeMobileNo());
                employeeDetailsResponseList.add(employeeDetailsResponse);

            }
            return employeeDetailsResponseList;
        }
    }

    @Override
    public EmployeeDetailsResponse getEmployeeDeatils(Integer employeeDetailsId) {
        Optional<EmployeeDetails>optionalEmployeeDetails=employeeDetailsRepository.findById(employeeDetailsId);
        if (optionalEmployeeDetails.isPresent())
        {
            EmployeeDetailsResponse employeeDetailsResponse=new EmployeeDetailsResponse();
            EmployeeDetails employeeDetails=optionalEmployeeDetails.get();
            List<PriviousProject> priviousProjectList=priviousProjectRepository.findPriviousProjectByEmployeeId(employeeDetailsId);
            List<Integer> priviousProjectIdList=new ArrayList<>();
            for (PriviousProject priviousProject:priviousProjectList)
            {
                priviousProjectIdList.add(priviousProject.getPriviousProjectId());
            }
            Optional<CurrentProject> optionalCurrentProject=currentProjectRepository.getCurrentProjectByEmployeeId(employeeDetailsId);
            if (optionalCurrentProject.isPresent())
            employeeDetailsResponse.setEmployeeCurrentProjectId(optionalCurrentProject.get().getCurrentProjectId());
            employeeDetailsResponse.setEmployeePriviousProjectIds(priviousProjectIdList);
            employeeDetailsResponse.setEmployeeId(employeeDetails.getEmployeeId());
            employeeDetailsResponse.setEmployeeCountry(employeeDetails.getEmployeeCountry());
            employeeDetailsResponse.setEmployeeEmail(employeeDetails.getEmployeeEmail());
            employeeDetailsResponse.setEmployeeAddressCity(employeeDetails.getEmployeeAddressCity());
            employeeDetailsResponse.setEmployeeFirstName(employeeDetails.getEmployeeFirstName());
            employeeDetailsResponse.setEmployeeLastName(employeeDetails.getEmployeeLastName());
            employeeDetailsResponse.setEmployeeDesignation(employeeDetails.getEmployeeDesignation());
            employeeDetailsResponse.setEmployeeSalaryPerMonth(employeeDetails.getEmployeeSalaryPerMonth());
            employeeDetailsResponse.setEmployeeGender(employeeDetails.getEmployeeGender());
            employeeDetailsResponse.setEmployeeImageUrl(employeeDetails.getEmployeeImageUrl());
            employeeDetailsResponse.setEmployeeMobileNo(employeeDetails.getEmployeeMobileNo());
            return employeeDetailsResponse;

        }
        else {
            throw new EmployeeDetailsNotFoundException("No any Employee exists with employee Id: "+employeeDetailsId);
        }
    }

    @Override
    public Integer postEmployeeDetails(EmployeeDetailsRequest employeeDetailsRequest) throws IOException {
        if(employeeDetailsRepository.existsById(employeeDetailsRequest.getEmployeeId()))
        {
            throw new EmployeeDetailsAlreadyExistsException("Employee With Employee Id "+employeeDetailsRequest.getEmployeeId()+" Already exists");
        }
        else if(employeeDetailsRepository.getEmployeeDetailsByEmployeeEmail(employeeDetailsRequest.getEmployeeEmail()).size()>0)
        {
            throw new EmployeeDetailsAlreadyExistsException("Employee Already With Email Id: "+employeeDetailsRequest.getEmployeeEmail());
        }
        else {
            FileReader fileReader=new FileReader("C:\\Users\\tribhuvan pal\\Desktop\\Interview_ems\\Employee_Management_Backend_Application\\employee_management_backend\\src\\main\\resources\\utilites_data.txt");
            Properties utilDataProperties=new Properties();
            utilDataProperties.load(fileReader);
            boolean flage=false;
            Properties properties=System.getProperties();
            EmployeeDetails employeeDetails=new EmployeeDetails();
            employeeDetails.setEmployeeAddressCity(employeeDetailsRequest.getEmployeeAddressCity());
            employeeDetails.setEmployeeCountry(employeeDetailsRequest.getEmployeeCountry());
            employeeDetails.setEmployeeEmail(employeeDetailsRequest.getEmployeeEmail());
            employeeDetails.setEmployeeLastName(employeeDetailsRequest.getEmployeeLastName());
            employeeDetails.setEmployeeFirstName(employeeDetailsRequest.getEmployeeFirstName());
            employeeDetails.setEmployeeId(employeeDetailsRequest.getEmployeeId());
            employeeDetails.setEmployeeDesignation(employeeDetailsRequest.getEmployeeDesignation());
            employeeDetails.setEmployeeSalaryPerMonth(employeeDetailsRequest.getEmployeeSalaryPerMonth());
            employeeDetails.setEmployeeGender(employeeDetailsRequest.getEmployeeGender());
            employeeDetails.setEmployeeImageUrl(employeeDetailsRequest.getEmployeeImageUrl());
            employeeDetails.setEmployeeMobileNo(employeeDetailsRequest.getEmployeeMobileNo());
            EmployeeLeave employeeLeave=new EmployeeLeave();
            employeeLeave.setEmployeeId(employeeDetails.getEmployeeId());
            employeeLeave.setNoOfEarnedLeaveTaken(0);
            employeeLeave.setNoOfEarnedLeaveAvailable(Integer.valueOf(utilDataProperties.getProperty("total_no_of_earn_for_new_employee")));
            employeeLeave.setNoOfLeaveWithoutPayTacken(0);
            employeeLeaveRepository.save(employeeLeave);

            return employeeDetailsRepository.save(employeeDetails).getEmployeeId();
        }
    }

    @Override
    public Integer deleteEmployeeDetails(Integer employeeDetailsId) {
      if (employeeDetailsRepository.existsById(employeeDetailsId))
      {
          if(priviousProjectRepository.getNoOfCountByEmployeeId(employeeDetailsId)>0)
          {
              priviousProjectRepository.deletePriviousProjectByEmployeeId(employeeDetailsId);
          }
          if(currentProjectRepository.getNoOfCountByEmployeeId(employeeDetailsId)>0)
          {
              currentProjectRepository.deleteCurrentProjectByEmployeeId(employeeDetailsId);
          }
          if(employeeLeaveRepository.existsById(employeeDetailsId))
          {
              employeeLeaveRepository.deleteById(employeeDetailsId);
          }
          if(employeeAttendanceRepository.existsById(employeeDetailsId))
          {
              employeeAttendanceRepository.deleteById(employeeDetailsId);
          }
          employeeDetailsRepository.deleteById(employeeDetailsId);
          return employeeDetailsId;
      }
      else {
          throw new EmployeeDetailsNotFoundException("Employee with employee Id: "+employeeDetailsId+"does not exists");
      }
    }

    @Override
    public Integer updateEmployeedetails(EmployeeDetailsRequest employeeDetailsRequest) {
        if (employeeDetailsRepository.existsById(employeeDetailsRequest.getEmployeeId())) {
            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setEmployeeAddressCity(employeeDetailsRequest.getEmployeeAddressCity());
            employeeDetails.setEmployeeCountry(employeeDetailsRequest.getEmployeeCountry());
            employeeDetails.setEmployeeEmail(employeeDetailsRequest.getEmployeeEmail());
            employeeDetails.setEmployeeLastName(employeeDetailsRequest.getEmployeeLastName());
            employeeDetails.setEmployeeFirstName(employeeDetailsRequest.getEmployeeFirstName());
            employeeDetails.setEmployeeId(employeeDetailsRequest.getEmployeeId());
            employeeDetails.setEmployeeDesignation(employeeDetailsRequest.getEmployeeDesignation());
            employeeDetails.setEmployeeSalaryPerMonth(employeeDetailsRequest.getEmployeeSalaryPerMonth());
            employeeDetails.setEmployeeGender(employeeDetailsRequest.getEmployeeGender());
            employeeDetails.setEmployeeImageUrl(employeeDetailsRequest.getEmployeeImageUrl());
            employeeDetails.setEmployeeMobileNo(employeeDetailsRequest.getEmployeeMobileNo());
            return employeeDetailsRepository.save(employeeDetails).getEmployeeId();
        }
        else {
            throw new EmployeeDetailsNotFoundException("Employee with employee Id: "+employeeDetailsRequest.getEmployeeId()+"does not exists");
        }
    }
}
