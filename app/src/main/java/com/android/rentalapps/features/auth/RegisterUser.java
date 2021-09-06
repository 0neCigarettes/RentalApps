package com.android.rentalapps.features.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.rentalapps.R;
import com.android.rentalapps.server.BaseURL;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterUser extends AppCompatActivity {

    Button doRegist, bLogin;
    TextInputEditText bFullname, bUsername, bPassword, bPhone, bEmail;
    ProgressDialog progressDialog;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mRequestQueue = Volley.newRequestQueue(this);

        bFullname = (TextInputEditText) findViewById(R.id.fullname);
        bUsername = (TextInputEditText) findViewById(R.id.username);
        bPassword = (TextInputEditText) findViewById(R.id.password);
        bPhone = (TextInputEditText) findViewById(R.id.phone);
        bEmail = (TextInputEditText) findViewById(R.id.email);
        bLogin = (Button) findViewById(R.id.back_login);
        doRegist = (Button) findViewById(R.id.do_regist);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUser.this, Login.class));
                Animatoo.animateSlideDown(RegisterUser.this);
            }
        });

        doRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                String sFullname = bFullname.getText().toString();
                String sUsername = bUsername.getText().toString();
                String sPassword = bPassword.getText().toString();
                String sPhone = bPhone.getText().toString();
                String sEmail = bEmail.getText().toString();
                String sAddress = null;
                String sProfilePhoto = null;

                if (sFullname.isEmpty()) {
                    StyleableToast.makeText(RegisterUser.this, "Nama lengkap tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
                } else if (sUsername.isEmpty()) {
                    StyleableToast.makeText(RegisterUser.this, "Username tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
                } else if (sPassword.isEmpty()) {
                    StyleableToast.makeText(RegisterUser.this, "Password tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
                } else if (sPhone.isEmpty()) {
                    StyleableToast.makeText(RegisterUser.this, "Nomor telepon tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
                } else if (sEmail.isEmpty()) {
                    StyleableToast.makeText(RegisterUser.this, "Email tidak boleh di kosongkan...", R.style.toastStyleWarning).show();
                } else {
                    registUser( sFullname, sUsername, sPassword, sPhone, sEmail, sAddress, sProfilePhoto);
                }
            }
        });
    }

    public void registUser( String fullname, String username, String password, String phone, String email, String address, String profilephoto) {

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("fullname", fullname);
        params.put("username", username);
        params.put("password", password);
        params.put("phone", phone);
        params.put("email", email);
        params.put("address", address);
        params.put("profilephoto", profilephoto);
        params.put("role", "1");

        progressDialog.setTitle("Mohon tunggu sebentar...");
        showDialog();

        final JsonObjectRequest req = new JsonObjectRequest(BaseURL.registerUser, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean statusMsg = response.getBoolean("error");

                            if (statusMsg == false) {
                                StyleableToast.makeText(RegisterUser.this, strMsg, R.style.toastStyleSuccess).show();
                                startActivity(new Intent(RegisterUser.this, Login.class));
                                Animatoo.animateSlideDown(RegisterUser.this);
                            } else {
                                StyleableToast.makeText(RegisterUser.this, strMsg, R.style.toastStyleWarning).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
        startActivity(new Intent(RegisterUser.this, Login.class));
        Animatoo.animateSlideDown(RegisterUser.this);
    }
}
