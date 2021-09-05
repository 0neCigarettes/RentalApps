package com.android.rentalapps.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.home.model.OrderJasa;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.Utils;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class BottomDialogs {

    public static void showPesananDialog(Activity activity, String title, OrderJasa data){
        String cityName = "";
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.item_bottom_dialogs, null);

        CircleImageView mImgPelanggan = customView.findViewById(R.id.mImgPelanggan);
        TextView mAlamat = customView.findViewById(R.id.mAlamat);
        TextView mNamaPelanggan = customView.findViewById(R.id.mNamaPelanggan);
        TextView mPhone = customView.findViewById(R.id.mPhone);
        TextView mOrderId = customView.findViewById(R.id.mOrderId);
        TextView mNamaMobil = customView.findViewById(R.id.mNamaMobil);
        TextView mHarga = customView.findViewById(R.id.mHarga);
        TextView mPlat = customView.findViewById(R.id.mPlat);

        if(data.getmImgPelanggan() != null){
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + data.getmImgPelanggan()).into(mImgPelanggan);
        }
        mAlamat.setText(data.getmAlamatPelanggan());
        mNamaPelanggan.setText(data.getmNamaPelanggan());
        mPhone.setText(data.getmPhonePelanggan());
        mOrderId.setText(data.getmOrderId());
        mNamaMobil.setText(data.getmNamaMobil());
        mHarga.setText(Utils.convertRupiah(data.getmHarga()));
        mPlat.setText(data.getmPlat());

        BottomDialog dialog = new BottomDialog.Builder(activity)
                .setTitle(data.getmNamaPelanggan())
                .setIcon(Drawable.createFromPath(App.getApplication().getString(R.string.img_url) + data.getmImgPelanggan()))
                .setCancelable(true)
                .setCustomView(customView)
                .build();
        dialog.show();
    }
}
