package com.example.Product.Repository;

import com.example.Product.Entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductrepositoryInterface extends MongoRepository<ProductEntity, String> {

    List<ProductEntity> getByPCatagory(String pCategory);
    ProductEntity getOneByPId(String id);
    void deleteAll();
    ProductEntity save(ProductEntity productEntity);

}
