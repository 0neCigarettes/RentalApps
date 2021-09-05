package com.android.rentalapps.features.customer.katalog.model;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    private String mId;
    @SerializedName("Order_id")
    private String mOrder_id;
    @SerializedName("customer_id")
    private String mCustomer_id;
    @SerializedName("product_id")
    private String mProduct_id;
    @SerializedName("namaMobil")
    private String mNamaMobil;
    @SerializedName("plat")
    private String mPlat;
    @SerializedName("harga")
    private String mHarga;
    @SerializedName("img")
    private String mImg;
    @SerializedName("status")
    private String mStatus;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmOrder_id() {
        return mOrder_id;
    }

    public void setmOrder_id(String mOrder_id) {
        this.mOrder_id = mOrder_id;
    }

    public String getmCustomer_id() {
        return mCustomer_id;
    }

    public void setmCustomer_id(String mCustomer_id) {
        this.mCustomer_id = mCustomer_id;
    }

    public String getmProduct_id() {
        return mProduct_id;
    }

    public void setmProduct_id(String mProduct_id) {
        this.mProduct_id = mProduct_id;
    }

    public String getmNamaMobil() {
        return mNamaMobil;
    }

    public void setmNamaMobil(String mNamaMobil) {
        this.mNamaMobil = mNamaMobil;
    }

    public String getmPlat() {
        return mPlat;
    }

    public void setmPlat(String mPlat) {
        this.mPlat = mPlat;
    }

    public String getmHarga() {
        return mHarga;
    }

    public void setmHarga(String mHarga) {
        this.mHarga = mHarga;
    }

    public String getmImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
