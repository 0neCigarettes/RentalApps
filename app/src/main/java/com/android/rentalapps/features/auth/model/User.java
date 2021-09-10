package com.android.rentalapps.features.auth.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class User {

    @SerializedName("id")
    String id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    private float distance;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }


    public static class Sortbyroll implements Comparator<User>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(User a, User b)
        {
//            return (int) (a.getDistance() - b.getDistance());
            return Float.valueOf(a.getDistance()).compareTo(Float.valueOf(b.getDistance()));
        }
    }
}
