package com.android.rentalapps.features.customer.home;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.rentalapps.R;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.customer.account.AccountFragmentUser;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.android.rentalapps.utils.Utils;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class MainUser extends AppCompatActivity {

    private static final String TAG = MainUser.class.getSimpleName();
    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;
    User profile;
    boolean BackPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        bottomNav = findViewById(R.id.bottom_nav);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        profile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );

        if (!Utils.isLoggedIn()) {
            bottomNav.setItemSelected(R.id.account, true);
            fragmentManager = getSupportFragmentManager();
            AccountFragmentUser accountFragment = new AccountFragmentUser();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, accountFragment).commit();
        } else {
            if (savedInstanceState == null) {
                bottomNav.setItemSelected(R.id.home, true);
                fragmentManager = getSupportFragmentManager();
                HomeFragmentUser homeFragment = new HomeFragmentUser();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            }
        }

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id) {
                    case R.id.home:
                        fragment = new HomeFragmentUser();
                        break;
                    case R.id.account:
                        fragment = new AccountFragmentUser();
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
