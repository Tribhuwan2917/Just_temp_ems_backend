package com.employee_management_backend_Application.service;

import com.employee_management_backend_Application.exception.MessageSenderException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
@Service
public class MessageSenderImpl implements MessageSender{
    @Autowired
    private SendSMSMessage sendSMSMessage;
    @Override
    public String sendSMS(SMSSenderRequest smsSenderRequest) throws IOException {
     boolean flage= sendSMSMessage.sendSMSMessage(smsSenderRequest);
     if (flage)
     {
         return "SMS Message Successfully Send";
     }
     else {
         System.out.println("SMS Message Failed");
         throw new MessageSenderException("Something Went Wrong, We Could Not Send The Message");
     }


    }

    @Override
    public String sendEmail(EmailSenderRequest emailSenderRequest) throws IOException {
        boolean flage=sendEmail( emailSenderRequest.getEmailSubject(),emailSenderRequest.getEmailMessage(),emailSenderRequest.getReceiverEmailId(),emailSenderRequest.getSenderEmailId(),emailSenderRequest.getEmailAttachment());
        if(flage)
        {
            return "Email Successfully send";
        }
        else {
            throw new MessageSenderException("Something Went Wrong , Email Could Not Send");
        }
    }
    public boolean sendEmail(String subject,String message,String to,String from,String attachment) throws IOException
    {

        FileReader fileReader=new FileReader("C:\\Users\\tribhuvan pal\\Desktop\\Interview_ems\\Employee_Management_Backend_Application\\employee_management_backend\\src\\main\\resources\\utilites_data.txt");
        Properties utilDataProperties=new Properties();
        utilDataProperties.load(fileReader);
        boolean flage=false;
        Properties properties=System.getProperties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.host",utilDataProperties.getProperty("host"));
        properties.put("mail.smtp.port",utilDataProperties.getProperty("port"));
        String userName=utilDataProperties.getProperty("userName");
        String password=utilDataProperties.getProperty("password");
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName,password);
            }
        });
        Message mimeMessage=new MimeMessage(session);
        try
        {
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            mimeMessage.setSubject(subject);
            if(attachment!=null&&attachment!="")
            {
                File file=new File(attachment);
                MimeBodyPart part1=new MimeBodyPart();
                part1.setText(message);
                MimeBodyPart part2=new MimeBodyPart();
                part2.attachFile(file);
                MimeMultipart mimeMultipart=new MimeMultipart();
                mimeMultipart.addBodyPart(part1);
                mimeMultipart.addBodyPart(part2);
                mimeMessage.setContent(mimeMultipart);
            }else {
                mimeMessage.setText(message);
            }
            Transport.send(mimeMessage);
            flage=true;

        }catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return flage;

    }
}
