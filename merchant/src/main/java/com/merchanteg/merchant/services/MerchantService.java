package com.merchanteg.merchant.services;

import com.merchanteg.merchant.dto.ProductMerchant;

import java.util.List;


public interface MerchantService {

     List<ProductMerchant> getByProductId(String productId);

     ProductMerchant save(ProductMerchant productMerchantEntity);

    ProductMerchant getOneByProductMerchantId(String productMerchantId);


}
