package com.android.rentalapps.features.JasaRental.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.account.AccountFragmentJasaRental;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.android.rentalapps.utils.Utils;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class MainJasaRental extends AppCompatActivity {

    private static final String TAG = MainJasaRental.class.getSimpleName();
    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;
    User profile;
    boolean BackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jasa_rental);

        bottomNav = findViewById(R.id.bottom_nav);

        profile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );

        if (!Utils.isLoggedIn()) {
            bottomNav.setItemSelected(R.id.account, true);
            fragmentManager = getSupportFragmentManager();
            AccountFragmentJasaRental accountFragment = new AccountFragmentJasaRental();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, accountFragment).commit();
        } else {
            if (savedInstanceState == null) {
                bottomNav.setItemSelected(R.id.home, true);
                fragmentManager = getSupportFragmentManager();
                HomeFragmentJasaRental homeFragment = new HomeFragmentJasaRental();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            }
        }

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id) {
                    case R.id.home:
                        fragment = new HomeFragmentJasaRental();
                        break;
                    case R.id.account:
                        fragment = new AccountFragmentJasaRental();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                } else {
                    Log.e(TAG, "Error creating fragment");
                }
            }
        });
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
    }
}
