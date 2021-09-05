package com.android.rentalapps.features.JasaRental.home;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rentalapps.features.JasaRental.home.model.OrderJasa;
import com.android.rentalapps.R;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.utils.App;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeJasaAdapter extends RecyclerView.Adapter<HomeJasaAdapter.ViewHolder> {
    private List<OrderJasa> listOrders;
    private HomeJasaAdapter.onSelected listener;
    Activity context;

    public interface onSelected {
        void onAcc(OrderJasa data);
        void onDone(OrderJasa data);
        void onTolak(OrderJasa data);
        void onCek(OrderJasa data);
    }

    public HomeJasaAdapter(List<OrderJasa> listOrders, Activity context, HomeJasaAdapter.onSelected listener){
        this.listOrders = listOrders;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeJasaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan, parent, false);
        HomeJasaAdapter.ViewHolder viewHolder = new HomeJasaAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HomeJasaAdapter.ViewHolder holder, final int position) {
        final OrderJasa listOrder = listOrders.get(position);
        Log.d("adapter", new Gson().toJson(listOrder));
        holder.mFullname.setText(listOrder.getmNamaPelanggan());
        holder.mPhone.setText(listOrder.getmPhonePelanggan());
        holder.mNamaMobil.setText(listOrder.getmNamaMobil());
        holder.mCek.setOnClickListener(v -> {listener.onCek(listOrder);});
        if(!listOrder.getmImgMobil().equals(null)){
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + listOrder.getmImgMobil()).into(holder.mFotoMobil);
        }
        if (listOrder.getmStatus().equals("1")){
            holder.mDone.setVisibility(View.GONE);
            holder.mTolak.setVisibility(View.VISIBLE);
            holder.mTolak.setOnClickListener(v -> {
                SweetDialogs.confirmDialog(context, "Tolak pesanan ini ?","Klik Ya untuk lanjutkan !","", String->listener.onTolak(listOrder));
            });
            holder.mAcc.setVisibility(View.VISIBLE);
            holder.mAcc.setOnClickListener(v -> {
                SweetDialogs.confirmDialog(context, "Terima pesanan ini ?","Klik Ya untuk lanjutkan !","", String->listener.onAcc(listOrder));
            });
        } else if (listOrder.getmStatus().equals("2")){
            holder.mTolak.setVisibility(View.GONE);
            holder.mAcc.setVisibility(View.GONE);
            holder.mDone.setVisibility(View.VISIBLE);
            holder.mDone.setOnClickListener(v -> {
                SweetDialogs.confirmDialog(context, "Selesaikan pesanan ini ?","Klik Ya untuk lanjutkan !","", String->listener.onDone(listOrder));
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listOrders == null)
            return 0;
        else
            return listOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mFotoMobil;
        TextView mFullname, mPhone, mNamaMobil;
        LinearLayout mCek;
        Button mAcc, mTolak, mDone;

        public ViewHolder(@NonNull View v) {
            super(v);
            mFotoMobil = v.findViewById(R.id.fotoMobil);
            mFullname = v.findViewById(R.id.fullnameUsers);
            mPhone = v.findViewById(R.id.phone);
            mNamaMobil = v.findViewById(R.id.namaMobil);
            mCek = v.findViewById(R.id.cek);
            mAcc = v.findViewById(R.id.acc);
            mTolak = v.findViewById(R.id.tolak);
            mDone = v.findViewById(R.id.done);
        }
    }
}
