package com.merchanteg.merchant.services.impl;

import com.merchanteg.merchant.dto.Merchant;
import com.merchanteg.merchant.dto.ProductMerchant;
import com.merchanteg.merchant.entity.MerchantEntity;
import com.merchanteg.merchant.entity.ProductMerchantEntity;
import com.merchanteg.merchant.repository.ProductMerchantRepository;
import com.merchanteg.merchant.services.MerchantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {


    @Autowired
    ProductMerchantRepository productMerchantRepository;


    @Override
    public List<ProductMerchant> getByProductId(String productId) {
        List<ProductMerchantEntity> productMerchantEntityList = productMerchantRepository.getByProductId(productId);
        List<ProductMerchant> productMerchants = new ArrayList<>();
        for(ProductMerchantEntity productMerchantEntity:productMerchantEntityList)
        {
            ProductMerchant productMerchant = new ProductMerchant();
            BeanUtils.copyProperties(productMerchantEntity,productMerchant);
            Merchant merchant = new Merchant();
            BeanUtils.copyProperties(productMerchantEntity.getMerchantEntity(),merchant);
            productMerchant.setMerchant(merchant);
            productMerchants.add(productMerchant);
        }
        return productMerchants;
    }

    @Override
    public ProductMerchant save(ProductMerchant productMerchant) {
        ProductMerchantEntity productMerchantEntity2 = new ProductMerchantEntity();
        BeanUtils.copyProperties(productMerchant,productMerchantEntity2);
        MerchantEntity merchantEntity = new MerchantEntity();
        BeanUtils.copyProperties(productMerchant.getMerchant(),merchantEntity);
        productMerchantEntity2.setMerchantEntity(merchantEntity);
        ProductMerchantEntity productMerchantEntity1 = productMerchantRepository.save(productMerchantEntity2);
        ProductMerchant productMerchant1 = new ProductMerchant();
        BeanUtils.copyProperties(productMerchantEntity1,productMerchant1);
        //BeanUtils.copyProperties(productMerchant,productMerchantEntity2);
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(productMerchantEntity1.getMerchantEntity(),merchant);
        productMerchant1.setMerchant(merchant);
        return productMerchant1;
    }

    @Override
    public ProductMerchant getOneByProductMerchantId(String productMerchantId) {
        ProductMerchant productMerchant = new ProductMerchant();
        ProductMerchantEntity productMerchantEntity = productMerchantRepository.getOneByProductMerchantId(productMerchantId);
        BeanUtils.copyProperties(productMerchantEntity,productMerchant);
        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(productMerchantEntity.getMerchantEntity(),merchant);
        productMerchant.setMerchant(merchant);
        return productMerchant;
    }


    
}
