package com.merchanteg.merchant.repository;

import com.merchanteg.merchant.entity.ProductMerchantEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMerchantRepository extends CrudRepository<ProductMerchantEntity,String> {

     List<ProductMerchantEntity> getByProductId(String productId);

     ProductMerchantEntity getOneByProductMerchantId(String productMerchantId);


}
