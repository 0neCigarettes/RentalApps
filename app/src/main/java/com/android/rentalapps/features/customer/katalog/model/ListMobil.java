package com.android.rentalapps.features.customer.katalog.model;

import com.google.gson.annotations.SerializedName;

public class ListMobil {
    @SerializedName("id")
    private String id;
    @SerializedName("idUser")
    private String idUser;
    @SerializedName("namaMobil")
    private String namaMobil;
    @SerializedName("plat")
    private String plat;
    @SerializedName("harga")
    private String harga;
    @SerializedName("img")
    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
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

    public String getImg() { return img; }

    public void setImg(String img) { this.img = img; }
}
