package com.merchanteg.merchant.dto;

public class Merchant {


    private String merchantId;
    private String merchantName;
    private int merchantRating;
    private float merchantScore;

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantRating=" + merchantRating +
                ", merchantScore=" + merchantScore +
                '}';
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(int merchantRating) {
        this.merchantRating = merchantRating;
    }

    public float getMerchantScore() {
        return merchantScore;
    }

    public void setMerchantScore(float merchantScore) {
        this.merchantScore = merchantScore;
    }

}
