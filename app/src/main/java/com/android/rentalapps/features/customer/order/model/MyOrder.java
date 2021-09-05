package com.android.rentalapps.features.customer.order.model;

import com.google.gson.annotations.SerializedName;

public class MyOrder {
    @SerializedName("idJasa")
    private String mIdJasa;
    @SerializedName("namaJasa")
    private String mNamaJasa;
    @SerializedName("alamatjasa")
    private String mAlamatJasa;
    @SerializedName("phoneJasa")
    private String mPhoneJasa;
    @SerializedName("id")
    private String mId;
    @SerializedName("order_id")
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

    public String getmIdJasa() {
        return mIdJasa;
    }

    public void setmIdJasa(String mIdJasa) {
        this.mIdJasa = mIdJasa;
    }

    public String getmNamaJasa() {
        return mNamaJasa;
    }

    public void setmNamaJasa(String mNamaJasa) {
        this.mNamaJasa = mNamaJasa;
    }

    public String getmAlamatJasa() {
        return mAlamatJasa;
    }

    public void setmAlamatJasa(String mAlamatJasa) {
        this.mAlamatJasa = mAlamatJasa;
    }

    public String getmPhoneJasa() {
        return mPhoneJasa;
    }

    public void setmPhoneJasa(String mPhoneJasa) {
        this.mPhoneJasa = mPhoneJasa;
    }

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
