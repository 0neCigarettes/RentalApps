package com.android.rentalapps.features.customer.order;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.rentalapps.R;
import com.android.rentalapps.features.customer.order.model.MyOrder;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<MyOrder> listOrders;
    private OrderAdapter.onSelected listener;
    Activity context;

    public interface onSelected {
        void onCall(MyOrder myOrder);
    }

    public OrderAdapter(List<MyOrder> listOrders, Activity context, OrderAdapter.onSelected listener) {
        this.listOrders = listOrders;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan_user, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder holder, final int position) {
        final MyOrder myOrder = listOrders.get(position);
                Picasso.get().load(App.getApplication().getString(R.string.img_url)  + myOrder.getmImg()).into(holder.mImgMobil);
                holder.mOrderId.setText(myOrder.getmOrder_id());
                holder.mNamaJasa.setText(myOrder.getmNamaJasa());
                holder.mNamaMobil.setText(myOrder.getmNamaMobil());
                holder.mHarga.setText(Utils.convertRupiah(myOrder.getmHarga()));
                holder.mPhone.setText(myOrder.getmPhoneJasa());
                holder.mPlat.setText(myOrder.getmPlat());
                if (myOrder.getmStatus().equals("1")){
                    holder.mStatus.setText("Menunggu Konfirmasi");
                } else if (myOrder.getmStatus().equals("2")) {
                    holder.mStatus.setText("Dalam Layanan");
                    holder.mStatus.setBackground(this.context.getResources().getDrawable(R.drawable.orange_round_badge));
                }
                holder.mCall.setOnClickListener(v->listener.onCall(myOrder));
    }

    @Override
    public int getItemCount() {
        if (listOrders == null)
            return 0;
        else
            return listOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mImgMobil;
        TextView mOrderId, mNamaJasa, mNamaMobil, mHarga, mPhone, mPlat, mStatus;
        Button mCall;
        public ViewHolder(@NonNull View v) {
            super(v);

            mImgMobil = v.findViewById(R.id.mImgMobil);
            mOrderId = v.findViewById(R.id.mOrderId);
            mNamaJasa = v.findViewById(R.id.mNamaJasa);
            mNamaMobil = v.findViewById(R.id.mNamaMobil);
            mHarga = v.findViewById(R.id.mHarga);
            mPhone = v.findViewById(R.id.mPhone);
            mPlat = v.findViewById(R.id.mPlat);
            mCall = v.findViewById(R.id.mCall);
            mStatus = v.findViewById(R.id.mStatus);
        }
    }
}
