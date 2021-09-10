package com.android.rentalapps.features.auth.login.presenter;

import com.android.rentalapps.features.auth.login.view.ILoginView;
import com.android.rentalapps.features.auth.model.LoginResponse;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.server.NetworkService;
import com.android.rentalapps.server.RestService;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.Prefs;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenter {
    final ILoginView view;
    public final Retrofit restService;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        restService = RestService.getRetroftInstance();
    }

    public boolean isLoggedIn() {
        return App.getPref().getBoolean(Prefs.PREF_IS_LOGEDIN, false);
    }

    public void storeProfile(String data) {
        App.getPref().put(Prefs.PREF_STORE_PROFILE, data);
        App.getPref().put(Prefs.PREF_IS_LOGEDIN, true);
    }

    public void login(User model) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).signin(model)
                .enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideLoadingIndicator();
                if (response.body().getSuccess()) {
                    App.getPref().put(Prefs.PREF_IS_LOGEDIN, true);
                    view.onSigninSuccess(response.body());
                } else
                    view.onSigninFailed(response.body().getmMsg());
            }

            @Override
            public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
                view.hideLoadingIndicator();
                view.onNetworkError(t.getLocalizedMessage());
            }
        });
    }
}
