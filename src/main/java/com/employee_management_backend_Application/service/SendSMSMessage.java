package com.employee_management_backend_Application.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Service
public class SendSMSMessage {
    public boolean sendSMSMessage(SMSSenderRequest smsSenderRequest)throws IOException
    {
        boolean flage=false;
        try {
            FileReader fileReader = new FileReader("C:\\Users\\tribhuvan pal\\Desktop\\Interview_ems\\Employee_Management_Backend_Application\\employee_management_backend\\src\\main\\resources\\utilites_data.txt");
            Properties utilDataProperties = new Properties();
            utilDataProperties.load(fileReader);
            Twilio.init(utilDataProperties.getProperty("account_sID"), utilDataProperties.getProperty("auth_token"));
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("+91"+smsSenderRequest.getEmployeeMobileNo()),
                            new com.twilio.type.PhoneNumber(utilDataProperties.getProperty("twilio_mobile_number")),
                            smsSenderRequest.getMessage())
                            .create();
              flage=true;
        }catch (Exception exception)
        {
            flage=false;
        }
        return flage;

    }
}
