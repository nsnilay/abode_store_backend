package com.example.Product.Service;

import com.example.Product.Entity.ProductEntity;
import com.example.Product.dto.Product;

import java.util.List;

public interface ProductServiceInterface {
    List<Product> getByPCatagory(String pCategory);
    Product getOneByPId(String id);
    void deleteAll();
    Product save(ProductEntity productEntity);
    List<ProductEntity> findAll();

}
