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
import com.android.rentalapps.features.auth.register.presenter.RegisJasaPresenter;
import com.android.rentalapps.features.auth.register.view.IRegistJasaView;
import com.android.rentalapps.ui.SweetDialogs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterJasaRental extends AppCompatActivity implements IRegistJasaView {

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

    SweetAlertDialog sweetAlertDialog;
    RegisJasaPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_jasa_rental);
        ButterKnife.bind(this);

        this.initViews();
    }

    @Override
    public void initViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading ...");
        presenter = new RegisJasaPresenter(this);
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
            StyleableToast.makeText(RegisterJasaRental.this, "Nama jasa rental tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        } else if (sUsername.isEmpty()) {
            StyleableToast.makeText(RegisterJasaRental.this, "Username tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        } else if (sPassword.isEmpty()) {
            StyleableToast.makeText(RegisterJasaRental.this, "Password tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        } else if (sPhone.isEmpty()) {
            StyleableToast.makeText(RegisterJasaRental.this, "Nomor telepon tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        } else if (sEmail.isEmpty()) {
            StyleableToast.makeText(RegisterJasaRental.this, "Email telepon tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
        }else {
            User model = new User();
            model.setFullname(sFullname);
            model.setUsername(sUsername);
            model.setPassword(sPassword);
            model.setPhone(sPhone);
            model.setEmail(sEmail);
            model.setRole("2");
            presenter.regisJasa(model);
        }
    }

    @Override
    public void onRegirSuccess(String mMsg) {
        SweetDialogs.commonSuccessWithIntent(this , "Silahkan Login !", string -> this.goToLogin());
    }

    @Override
    public void onRegisFailed(String mMsg) {
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
        startActivity(new Intent(RegisterJasaRental.this, Login.class));
        Animatoo.animateSlideDown(RegisterJasaRental.this);
    }

    @OnClick(R.id.back_login)
    void goToLogin() {
        startActivity(new Intent(RegisterJasaRental.this, Login.class));
        Animatoo.animateSlideDown(RegisterJasaRental.this);
    }
}
