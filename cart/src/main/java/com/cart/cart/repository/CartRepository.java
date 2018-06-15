package com.cart.cart.repository;

import com.cart.cart.entity.CartEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<CartEntity, String> {

    CartEntity save(CartEntity cartEntity);
    CartEntity getOneById(String id);
    List<CartEntity> getByEmailId(String emailId);
    void delete(CartEntity cartEntity);
    boolean existsById(String id);

}
