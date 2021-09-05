package com.android.rentalapps.features.JasaRental.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
    @SerializedName("status")
    private Boolean mStatus;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private List<OrderJasa> mResult;

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

    public List<OrderJasa> getmResult() {
        return mResult;
    }

    public void setmResult(List<OrderJasa> mResult) {
        this.mResult = mResult;
    }
}
