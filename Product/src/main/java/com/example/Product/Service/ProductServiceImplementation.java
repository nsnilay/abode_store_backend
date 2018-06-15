package com.example.Product.Service;

import com.example.Product.Entity.ProductEntity;
import com.example.Product.Repository.ProductrepositoryInterface;
import com.example.Product.dto.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImplementation implements ProductServiceInterface{

    @Autowired
    ProductrepositoryInterface productrepositoryInterface;


    @Override
    public List<Product> getByPCatagory(String pCategory)
    {
        List<ProductEntity> productEntities = productrepositoryInterface.getByPCatagory(pCategory);
        List<Product> products = new ArrayList<>();
        for (ProductEntity productEntity:productEntities)
        {
            Product product = new Product();
            BeanUtils.copyProperties(productEntity,product);
            products.add(product);
        }
        return products;
    }

    @Override
    public Product getOneByPId(String id)
    {
        ProductEntity productEntity =  productrepositoryInterface.getOneByPId(id);
        Product product = new Product();
        BeanUtils.copyProperties(productEntity,product);
        return product;
    }

    @Override
    public void deleteAll() {
        productrepositoryInterface.deleteAll();

    }

    @Override
    public Product save(ProductEntity productEntity) {
        Product product = new Product();
        ProductEntity entity =productrepositoryInterface.save(productEntity);
        BeanUtils.copyProperties(entity,product);
        return product;
    }

    @Override
    public List<ProductEntity> findAll() {
        return productrepositoryInterface.findAll();
    }
}
