package com.android.rentalapps.features.customer.katalog.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListMobilResponse {
    @SerializedName("status")
    private Boolean mStatus;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private List<ListMobil> mResult;

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

    public List<ListMobil> getmResult() {
        return mResult;
    }

    public void setmResult(List<ListMobil> mResult) {
        this.mResult = mResult;
    }
}
