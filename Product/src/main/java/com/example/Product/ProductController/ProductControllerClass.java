package com.example.Product.ProductController;

import com.example.Product.Entity.ProductEntity;
import com.example.Product.Service.ProductServiceInterface;
import com.example.Product.dto.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductControllerClass {

    @Autowired
    ProductServiceInterface productServiceInterface;


    @RequestMapping(method = RequestMethod.GET,value = "/product/getByCategory/{category}")
    public List<Product> getByCategory(@PathVariable("category")String category)
    {
        return productServiceInterface.getByPCatagory(category);
    }

    @RequestMapping("/product/delete")
    public void delete()
    {
        productServiceInterface.deleteAll();
    }

    // Method for testing prupose
    @RequestMapping(method = RequestMethod.GET, value = "/getAll")
    public List<ProductEntity> findAll(){
        return productServiceInterface.findAll();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/product/insert")
    public Boolean saveToDb(@RequestBody Product productEntity)
    {
        try {
            ProductEntity productEntity1 = new ProductEntity();
            BeanUtils.copyProperties(productEntity,productEntity1);
            productServiceInterface.save(productEntity1);
            return true;
        }
        catch (Exception exception)
        {
            System.out.println(exception.toString());
            return false;
        }
    }


    @RequestMapping(method = RequestMethod.GET,value = "/product/getByPId/{pId}")
    public Product getByPId(@PathVariable("pId") String pId)
    {
        return productServiceInterface.getOneByPId(pId);
    }

    @RequestMapping(value = "/getProductNameAndUrl/{id}")
    public ResponseEntity<String> getEmployeeById (@PathVariable("id") String id) {
        Product product = productServiceInterface.getOneByPId(id);
        String pNameUrl;
        String productUrl = product.getpImageUrl();
        String productName = product.getpName();
        pNameUrl = productName + ',' + productUrl;

        return new ResponseEntity<String>(pNameUrl.toString(), HttpStatus.OK);
    }

}
