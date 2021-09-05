package com.android.rentalapps.features.customer.order.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderResponse {
    @SerializedName("status")
    private Boolean mStatus;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private List<MyOrder> mResult;

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

    public List<MyOrder> getmResult() {
        return mResult;
    }

    public void setmResult(List<MyOrder> mResult) {
        this.mResult = mResult;
    }
}
