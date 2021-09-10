package com.android.rentalapps.features.auth.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.rentalapps.R;
import com.android.rentalapps.features.auth.login.Login;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.auth.register.presenter.RegisUserPresenter;
import com.android.rentalapps.features.auth.register.view.IRegisUserView;
import com.android.rentalapps.ui.SweetDialogs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterUser extends AppCompatActivity implements IRegisUserView {

    @BindView(R.id.do_regist)
    Button doRegist;
    @BindView(R.id.back_login)
    Button bLogin;
    @BindView(R.id.fullname)
    TextInputEditText bFullname;
    @BindView(R.id.username)
    TextInputEditText bUsername;
    @BindView(R.id.password)
    TextInputEditText bPassword;
    @BindView(R.id.phone)
    TextInputEditText bPhone;
    @BindView(R.id.email)
    TextInputEditText bEmail;

    RegisUserPresenter presenter;
    SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ButterKnife.bind(this);
        this.initViews();
    }

    @Override
    public void initViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading ...");
        presenter = new RegisUserPresenter(this);
        doRegist.setOnClickListener(v -> {
            this.onRegis();
        });
    }

    @Override
    public void onRegis() {
        String sFullname = bFullname.getText().toString();
        String sUsername = bUsername.getText().toString();
        String sPassword = bPassword.getText().toString();
        String sPhone = bPhone.getText().toString();
        String sEmail = bEmail.getText().toString();

        if (sFullname.isEmpty()) {
            StyleableToast.makeText(RegisterUser.this, "Nama tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        } else if (sUsername.isEmpty()) {
            StyleableToast.makeText(RegisterUser.this, "Username tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        } else if (sPassword.isEmpty()) {
            StyleableToast.makeText(RegisterUser.this, "Password tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        } else if (sPhone.isEmpty()) {
            StyleableToast.makeText(RegisterUser.this, "Nomor telepon tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        } else if (sEmail.isEmpty()) {
            StyleableToast.makeText(RegisterUser.this, "Email telepon tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        }else {
            User model = new User();
            model.setFullname(sFullname);
            model.setUsername(sUsername);
            model.setPassword(sPassword);
            model.setPhone(sPhone);
            model.setEmail(sEmail);
            model.setRole("1");
            presenter.regisUser(model);
        }
    }

    @Override
    public void onRegisSuccess(String mMsg) {
        SweetDialogs.commonSuccessWithIntent(this , "Silahkan Login !", string -> this.goToLogin());
    }

    @Override
    public void onRegisFailed(String mMsg) {
        Log.d("err", mMsg);
        SweetDialogs.commonError(this, mMsg , false);
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
        startActivity(new Intent(RegisterUser.this, Login.class));
        Animatoo.animateSlideDown(RegisterUser.this);
    }

    @OnClick(R.id.back_login)
    void goToLogin() {
        startActivity(new Intent(RegisterUser.this, Login.class));
        Animatoo.animateSlideDown(RegisterUser.this);
    }
}
