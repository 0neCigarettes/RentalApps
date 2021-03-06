package com.android.rentalapps.features.customer.home.Model;

import com.google.gson.annotations.SerializedName;

public class ListJasaModel {
    @SerializedName("id")
    String id;
    @SerializedName("nik")
    String nik;
    @SerializedName("fullname")
    String fullname;
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("phone")
    String phone;
    @SerializedName("address")
    String address;
    @SerializedName("profilephoto")
    String profilephoto;
    @SerializedName("email")
    String email;
    @SerializedName("plat")
    String plat;
    @SerializedName("status")
    String status;
    @SerializedName("role")
    String role;
    @SerializedName("lati")
    String lati;
    @SerializedName("longi")
    String longi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }
}
