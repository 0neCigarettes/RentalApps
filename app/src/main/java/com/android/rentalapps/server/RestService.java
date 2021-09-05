package com.android.rentalapps.server;

import com.android.rentalapps.R;
import com.android.rentalapps.utils.App;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {
    private static final String NewBASE_URL = App.getApplication().getString(R.string.base_url);
    private static Retrofit retrofit = null ;

    public static Retrofit getRetroftInstance() {
        if (retrofit == null) {

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(NewBASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
