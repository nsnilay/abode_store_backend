package com.example.Login.UserRepository;

import com.example.Login.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepositoryInterface extends CrudRepository<UserEntity,String> {

     Boolean existsByEmailIdAndPassword(String emailId,String password);
     UserEntity getOneByEmailId(String emailId);
     Boolean existsByEmailId(String emailId);
     void deleteAll();

}
