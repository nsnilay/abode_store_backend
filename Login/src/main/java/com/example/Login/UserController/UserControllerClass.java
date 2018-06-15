package com.example.Login.UserController;

import com.example.Login.Entity.UserEntity;
import com.example.Login.UserService.UserServiceInterface;
import com.example.Login.dto.User;
import com.example.Login.dto.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.mail.*;

@RestController
public class UserControllerClass {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @RequestMapping(method = RequestMethod.POST,value = "/user/login")
    public Boolean getAuthentication(@RequestBody UserLogin userLogin) {
        UserEntity userEntity = new UserEntity();
        try {
            userServiceInterface.existsByEmailIdAndPassword(userLogin.getEmailId(), userLogin.getPassword());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="/user/register")
    public Boolean insert(@RequestBody User user) throws MessagingException
    {
        //if(!userServiceInterface.existsByEmailId(user.getEmailId())){
            userServiceInterface.save(user);
            String msg = "Hi";
            return userServiceInterface.sendEmail(user.getEmailId(),msg);
        //}
        //else
            //return false;
    }

    @RequestMapping("/user/delete")
    public void delete()
    {
        userServiceInterface.deleteAll();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/user/update")
    public boolean updateProfile(@RequestBody User user) {
        User userCopy = userServiceInterface.getOneByEmailId(user.getEmailId());
        userCopy.setPassword(user.getPassword());
        userCopy.setUserName(user.getUserName());
        userCopy.setAddress(user.getAddress());
        userCopy.setContactNumber(user.getContactNumber());
        if(userServiceInterface.save(userCopy))
            return true;
        else
            return false;
    }

}
