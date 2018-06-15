package com.merchanteg.merchant.dto;

public class ProductMerchant{

    private String productMerchantId;
    private String productId;
    private String price;
    private int currentStock;
    private int numberOfOrdersSold;
    private String productRating;
    private int numberOfOfferings;
    private Merchant merchant;


    @Override
    public String toString() {
        return "ProductMerchant{" +
                "productMerchantId='" + productMerchantId + '\'' +
                ", productId='" + productId + '\'' +
                ", price='" + price + '\'' +
                ", currentStock=" + currentStock +
                ", numberOfOrdersSold=" + numberOfOrdersSold +
                ", productRating='" + productRating + '\'' +
                ", numberOfOfferings=" + numberOfOfferings +
                '}';
    }

    public  Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
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


}
