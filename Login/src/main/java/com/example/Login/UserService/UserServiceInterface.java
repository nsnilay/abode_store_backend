package com.example.Login.UserService;

import com.example.Login.Entity.UserEntity;
import com.example.Login.dto.User;

import javax.mail.MessagingException;
import java.util.List;

public interface UserServiceInterface {

    //Todo : Phani : remove commented code

     Boolean existsByEmailIdAndPassword(String emailId,String password);
     boolean save(User user);
     Boolean existsByEmailId(String emailId);
     void deleteAll();
     boolean sendEmail(String emailId,String message) throws MessagingException;
     User getOneByEmailId(String emailId);
}
