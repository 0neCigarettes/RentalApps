package com.android.rentalapps.features.customer.home.Model;

import com.android.rentalapps.features.auth.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListJasaResponse {
    @SerializedName("status")
    private Boolean mStatus;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private List<User> mResult;

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

    public List<User> getmResult() {
        return mResult;
    }

    public void setmResult(List<User> mResult) {
        this.mResult = mResult;
    }
}
