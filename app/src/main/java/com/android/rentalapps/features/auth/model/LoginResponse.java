package com.android.rentalapps.features.auth.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private Boolean mStatus;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("result")
    private User mResult;

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

    public User getmResult() {
        return mResult;
    }
    public void setmResult(User mResult) {
        this.mResult = mResult;
    }

    public Boolean getSuccess() { return mStatus; }
    public void  setSuccess(Boolean success) { mStatus = success; }
}
