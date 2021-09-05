package com.android.rentalapps.features.JasaRental.home.model;

import com.google.gson.annotations.SerializedName;

public class OrderJasa {
    @SerializedName("id")
    private String mId;
    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("namaMobil")
    private String mNamaMobil;
    @SerializedName("harga")
    private String mHarga;
    @SerializedName("plat")
    private String mPlat;
    @SerializedName("imgMobil")
    private String mImgMobil;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("namaPelanggan")
    private String mNamaPelanggan;
    @SerializedName("alamatPelanggan")
    private String mAlamatPelanggan;
    @SerializedName("phonePelanggan")
    private String mPhonePelanggan;
    @SerializedName("imgPelanggan")
    private String mImgPelanggan;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmOrderId() {
        return mOrderId;
    }

    public void setmOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public String getmNamaMobil() {
        return mNamaMobil;
    }

    public void setmNamaMobil(String mNamaMobil) {
        this.mNamaMobil = mNamaMobil;
    }

    public String getmHarga() {
        return mHarga;
    }

    public void setmHarga(String mHarga) {
        this.mHarga = mHarga;
    }

    public String getmPlat() {
        return mPlat;
    }

    public void setmPlat(String mPlat) {
        this.mPlat = mPlat;
    }

    public String getmImgMobil() {
        return mImgMobil;
    }

    public void setmImgMobil(String mImgMobil) {
        this.mImgMobil = mImgMobil;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmNamaPelanggan() {
        return mNamaPelanggan;
    }

    public void setmNamaPelanggan(String mNamaPelanggan) {
        this.mNamaPelanggan = mNamaPelanggan;
    }

    public String getmAlamatPelanggan() {
        return mAlamatPelanggan;
    }

    public void setmAlamatPelanggan(String mAlamatPelanggan) {
        this.mAlamatPelanggan = mAlamatPelanggan;
    }

    public String getmPhonePelanggan() {
        return mPhonePelanggan;
    }

    public void setmPhonePelanggan(String mPhonePelanggan) {
        this.mPhonePelanggan = mPhonePelanggan;
    }

    public String getmImgPelanggan() {
        return mImgPelanggan;
    }

    public void setmImgPelanggan(String mImgPelanggan) {
        this.mImgPelanggan = mImgPelanggan;
    }
}
