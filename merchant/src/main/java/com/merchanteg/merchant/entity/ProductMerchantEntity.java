package com.merchanteg.merchant.entity;

import javax.persistence.*;

@Entity
@Table(name = ProductMerchantEntity.TABLE_NAME)
public class ProductMerchantEntity {


    static final String TABLE_NAME = "ProductMerchant";
    static final String ID_COLUMN = "id";

    @Id
    private String productMerchantId;
    //private String merchantId;
    private String productId;
    private String price;
    private int currentStock;
    private int numberOfOrdersSold;
    private String productRating;
    private int numberOfOfferings;

    @ManyToOne(cascade = CascadeType.ALL)
    private MerchantEntity merchantEntity;


    @Override
    public String toString() {
        return "ProductMerchantEntity{" +
                "productMerchantId='" + productMerchantId + '\'' +
               // ", merchantId='" + merchantId + '\'' +
                ", productId='" + productId + '\'' +
                ", price='" + price + '\'' +
                ", currentStock='" + currentStock + '\'' +
                ", numberOfOrdersSold='" + numberOfOrdersSold + '\'' +
                ", productRating='" + productRating + '\'' +
                ", numberOfOfferings='" + numberOfOfferings + '\'' +
                ", merchantEntity=" + merchantEntity +
                '}';
    }

    public String getProductMerchantId() {
        return productMerchantId;
    }

    public void setProductMerchantId(String productMerchantId) {
        this.productMerchantId = productMerchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getNumberOfOrdersSold() {
        return numberOfOrdersSold;
    }

    public void setNumberOfOrdersSold(int numberOfOrdersSold) {
        this.numberOfOrdersSold = numberOfOrdersSold;
    }

    public String getProductRating() {
        return productRating;
    }

    public void setProductRating(String productRating) {
        this.productRating = productRating;
    }

    public int getNumberOfOfferings() {
        return numberOfOfferings;
    }

    public void setNumberOfOfferings(int numberOfOfferings) {
        this.numberOfOfferings = numberOfOfferings;
    }

    public MerchantEntity getMerchantEntity() {
        return merchantEntity;
    }

    public void setMerchantEntity(MerchantEntity merchantEntity) {
        this.merchantEntity = merchantEntity;
    }
}
