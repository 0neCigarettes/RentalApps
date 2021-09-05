package com.android.rentalapps.features.JasaRental.product.model;

import com.google.gson.annotations.SerializedName;

public class ProductModel {

    @SerializedName("id")
    private String id;
    @SerializedName("idUser")
    private String userId;
    @SerializedName("namaMobil")
    private String namaMobil;
    @SerializedName("plat")
    private String plat;
    @SerializedName("harga")
    private String harga;
    @SerializedName("img")
    private String mImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNamaMobil() {
        return namaMobil;
    }

    public void setNamaMobil(String namaMobil) {
        this.namaMobil = namaMobil;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getmImg() {
        return mImg;
    }

    public void setmImg(String mImg) {
        this.mImg = mImg;
    }
}
