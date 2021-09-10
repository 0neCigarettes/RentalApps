package com.android.rentalapps.features.auth.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.home.MainJasaRental;
import com.android.rentalapps.features.auth.login.presenter.LoginPresenter;
import com.android.rentalapps.features.auth.login.view.ILoginView;
import com.android.rentalapps.features.auth.model.LoginResponse;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.auth.register.RegisterJasaRental;
import com.android.rentalapps.features.auth.register.RegisterUser;
import com.android.rentalapps.features.customer.home.MainUser;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity implements ILoginView {

    @BindView(R.id.do_login)
    Button doLogin;
    @BindView(R.id.username)
    TextInputEditText bUsername;
    @BindView(R.id.password)
    TextInputEditText bPassword;
    @BindView(R.id.regist_pengguna)
    LinearLayout bRegistUser;
    @BindView(R.id.regist_driver)
    LinearLayout bRegistDriver;
    @BindView(R.id.mCs)
    TextView mCs;

    LoginPresenter presenter;
    SweetAlertDialog sweetAlertDialog;
    LoginResponse mProfile;
    User model;
    boolean BackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this);
        if (presenter.isLoggedIn()) {
            model = (User) GsonHelper.parseGson(
                    App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                    new User()
            );

            if (model.getRole().equals("1")) {
                startActivity(new Intent(this, MainUser.class));
                Animatoo.animateSlideDown(this);
            } else if (model.getRole().equals("2")) {
                startActivity(new Intent(Login.this, MainJasaRental.class));
                Animatoo.animateSlideDown(this);
            }
        } else {
            this.initViews();
        }
    }

    @OnClick(R.id.regist_pengguna)
    void goToRegisUser(){
        startActivity(new Intent(Login.this, RegisterUser.class));
        Animatoo.animateSlideUp(Login.this);
    }

    @OnClick(R.id.regist_driver)
    void goToRegisJasa() {
        startActivity(new Intent(Login.this, RegisterJasaRental.class));
        Animatoo.animateSlideUp(Login.this);
    }

    @OnClick(R.id.do_login)
    void login() {
        String username = bUsername.getText().toString();
        String pass = bPassword.getText().toString();
        if (username.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Username tidak boleh kosong", R.style.toastStyleWarning).show();
        } else if (pass.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Password tidak boleh kosong", R.style.toastStyleWarning).show();
        } else {
            model = new User();
            model.setUsername(username);
            model.setPassword(pass);
            presenter.login(model);
        }
    }

    @Override
    public void initViews() {
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading ...");
    }

    @Override
    public void onSigninSuccess(LoginResponse response) {
        presenter.storeProfile(new Gson().toJson(response.getmResult()));
        Log.d("login", new Gson().toJson(response));
        if (response.getmResult().getRole().equals("1")) {
            startActivity(new Intent(this, MainUser.class));
            Animatoo.animateSlideDown(this);
        } else if (response.getmResult().getRole().equals("2")) {
            startActivity(new Intent(this, MainJasaRental.class));
            Animatoo.animateSlideDown(this);
        }
    }

    @Override
    public void onSigninFailed(String cause) {
        StyleableToast.makeText(Login.this, cause, R.style.toastStyleWarning).show();
    }

    @Override
    public void onNetworkError(String cause) {
        Log.e("errornya", cause);
        SweetDialogs.endpointError(this);
    }

    @Override
    public void showLoadingIndicator() {
        sweetAlertDialog.show();
    }

    @Override
    public void hideLoadingIndicator() {
        sweetAlertDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (BackPress) {
            super.onBackPressed();
            return;
        }
        this.BackPress = true;
        StyleableToast.makeText(this, "Tekan sekali lagi untuk keluar...", R.style.toastStyleDefault).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                BackPress = false;
            }
        }, 2000);
        Animatoo.animateZoom(Login.this);
    }

    @OnClick(R.id.mCs)
    void Chat () {
        final String phoneD = App.getApplication().getString(R.string.nomorAdmin);
        final String str1 = phoneD.replaceFirst("0", "+62");
        String url = "https://api.whatsapp.com/send?phone=" + str1;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setPackage("com.whatsapp");
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
