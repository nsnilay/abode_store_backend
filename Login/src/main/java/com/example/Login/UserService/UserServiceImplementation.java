package com.example.Login.UserService;

//Todo : Phani : remove unused imports
import com.example.Login.Entity.UserEntity;
import com.example.Login.UserRepository.UserRepositoryInterface;
import com.example.Login.dto.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

@Service
public class UserServiceImplementation implements UserServiceInterface{

    @Value("${mail.smtp.userName}")
    private String userName;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttls;

    @Value("${spring.mail.properties.mail.host}")
    private String host;

    @Value("${spring.mail.properties.mail.port}")
    private String port;

    @Autowired
    UserRepositoryInterface userRepositoryInterface;

    @Override
    public Boolean existsByEmailIdAndPassword(String emailId, String password)
    {
        return userRepositoryInterface.existsByEmailIdAndPassword(emailId,password);
    }

    @Override
    public Boolean existsByEmailId(String emailId) {
        return userRepositoryInterface.existsByEmailId(emailId);
    }

    @Override
    public void deleteAll() {
        userRepositoryInterface.deleteAll();
    }

    @Override
    public boolean sendEmail(String emailId, String message) throws MessagingException{
        Properties properties = new Properties();

        properties.put("mail.smtp.auth",auth);
        properties.put("mail.smtp.starttls.enable",starttls);
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port",port);

        javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName,password);
            }
        });

        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(userName,false));
        (mimeMessage).setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailId));
        mimeMessage.setSubject("Welcome to this App");
        mimeMessage.setContent("Thank you for registering","text/html");
        mimeMessage.setSentDate(new Date());
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent("textMessage","text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        try {
            Transport.send(mimeMessage);
            return true;
        }
        catch (Exception exception)
        {
            System.out.println(exception.toString());
            return false;
        }

    }

    @Override
    public User getOneByEmailId(String emailId) {
        UserEntity userEntity = userRepositoryInterface.getOneByEmailId(emailId);
        User user = new User();
        BeanUtils.copyProperties(userEntity,user);
        return user;
    }

    @Override
    public boolean save(User user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        try {
            UserEntity userEntityTemp = userRepositoryInterface.save(userEntity);
            return true;
        }
        catch (Exception exception)
        {
            exception.toString();
            return false;
        }
    }
}
