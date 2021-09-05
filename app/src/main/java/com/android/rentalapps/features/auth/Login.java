package com.android.rentalapps.features.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.home.MainJasaRental;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.server.BaseURL;
import com.android.rentalapps.features.customer.home.MainUser;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.android.rentalapps.utils.Utils;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    Button doLogin;
    TextInputEditText bUsername, bPassword;
    ProgressDialog progressDialog;
    LinearLayout bRegistUser, bRegistDriver;

    private RequestQueue mRequestQueue;
    User profile;
    boolean BackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bRegistUser = (LinearLayout) findViewById(R.id.regist_pengguna);
        bRegistDriver = (LinearLayout) findViewById(R.id.regist_driver);
        doLogin = (Button) findViewById(R.id.do_login);

        bUsername = (TextInputEditText) findViewById(R.id.username);
        bPassword = (TextInputEditText) findViewById(R.id.password);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mRequestQueue = Volley.newRequestQueue(this);

        profile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );

        if(Utils.isLoggedIn()){
            int dRoll = Integer.parseInt(profile.getRole());
            if (dRoll == 1){
                Intent i = new Intent(this , MainUser.class);
                startActivity(i);
                finish();
            }else if(dRoll == 2){
                Intent i = new Intent(this , MainJasaRental.class);
                startActivity(i);
                finish();
            }
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        bRegistUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterUser.class));
                Animatoo.animateSlideUp(Login.this);
            }
        });

        bRegistDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterJasaRental.class));
                Animatoo.animateSlideUp(Login.this);
            }
        });

        doLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUsername = bUsername.getText().toString();
                String sPassword = bPassword.getText().toString();

                if (sUsername.isEmpty()) {
                    StyleableToast.makeText(Login.this, "Username tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
                } else if (sPassword.isEmpty()) {
                    StyleableToast.makeText(Login.this, "Password tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
                } else {
                    loginAccess(sUsername, sPassword);
                }
            }
        });
    }

    public void loginAccess(String username, String password) {

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("username", username);
        params.put("password", password);

        progressDialog.setTitle("Mohon tunggu sebentar...");
        showDialog();

        final JsonObjectRequest req = new JsonObjectRequest(BaseURL.login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean statusMsg = response.getBoolean("status");
                            if (statusMsg != false) {
                                StyleableToast.makeText(Login.this, strMsg, R.style.toastStyleSuccess).show();

                                JSONObject user = response.getJSONObject("result");
                                Log.d("user", new Gson().toJson(user));
                                String tRole = user.getString("role");
                                App.getPref().put(Prefs.PREF_IS_LOGEDIN, true);
                                Utils.storeProfile(user.toString());

                                if (tRole.equals("1")) {
                                    startActivity(new Intent(Login.this, MainUser.class));
                                    Animatoo.animateSlideDown(Login.this);
                                } else {
                                    startActivity(new Intent(Login.this, MainJasaRental.class));
                                    Animatoo.animateSlideDown(Login.this);
                                }
                            } else {
                                StyleableToast.makeText(Login.this, strMsg, R.style.toastStyleWarning).show();
                            }
                        } catch (JSONException e) {
                            Log.d("err : ", e.getMessage().toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.dialog_loading);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.setContentView(R.layout.dialog_loading);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
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
}
