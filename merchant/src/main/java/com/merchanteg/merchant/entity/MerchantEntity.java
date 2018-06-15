package com.merchanteg.merchant.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = MerchantEntity.TABLE_NAME)
public class MerchantEntity {
    static final String TABLE_NAME = "Merchant";

    @Id
    private String merchantId;
    private String merchantName;
    private int merchantRating;
    private float merchantScore;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantRating='" + merchantRating + '\'' +
                ", merchantScore='" + merchantScore + '\'' +
                '}';
    }
}
