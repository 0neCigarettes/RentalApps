package com.android.rentalapps.features.JasaRental.account.profile.presenter;

import android.util.Log;

import com.android.rentalapps.features.JasaRental.account.profile.view.IUpdateJasaView;
import com.android.rentalapps.features.auth.model.LoginResponse;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.server.NetworkService;
import com.android.rentalapps.server.RestService;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.Prefs;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileJasaPresenter {
    final IUpdateJasaView view;
    public final Retrofit restService;

    public ProfileJasaPresenter(IUpdateJasaView view) {
        this.view = view;
        restService = RestService.getRetroftInstance();
    }

    public void profileUpdate(User model, File myImg, String path) {
        Log.d("model", new Gson().toJson(model));
        view.showLoadingIndicator();
        MultipartBody.Part img;
        RequestBody requestFile;
        if (myImg != null) {
            File file = new File(path);
            requestFile =  RequestBody.create(MediaType.parse("multipart/form-data"), file);
            img = MultipartBody.Part.createFormData("img", myImg.getName(), requestFile);
        } else {
            img = null;
            Log.d("img", "kosong");
        }
        restService.create(NetworkService.class).profile(img, model)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus()) {
                            App.getPref().put(Prefs.PREF_STORE_PROFILE, new Gson().toJson(response.body().getmResult()));
                            view.onSubmitSuccses(response.body().getmMsg());
                        } else {
                            view.onSubmitFailed(response.body().getmMsg());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }

}
