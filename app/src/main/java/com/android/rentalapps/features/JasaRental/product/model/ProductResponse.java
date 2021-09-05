package com.android.rentalapps.features.JasaRental.product.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    @SerializedName("status")
    private Boolean mStatus;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private List<ProductModel> mResult;

    public Boolean getmStatus() {
        return mStatus;
    }

    public void setmStatus(Boolean mStatus) {
        this.mStatus = mStatus;
    }

    public String getmMsg() {
        return mMsg;
    }

    public void setmMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public List<ProductModel> getmResult() {
        return mResult;
    }

    public void setmResult(List<ProductModel> mResult) {
        this.mResult = mResult;
    }
}
