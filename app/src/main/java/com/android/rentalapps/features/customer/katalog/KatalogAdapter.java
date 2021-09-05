package com.android.rentalapps.features.customer.katalog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.rentalapps.R;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.features.customer.katalog.model.ListMobil;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.Utils;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class KatalogAdapter extends RecyclerView.Adapter<KatalogAdapter.ViewHolder> {
    private List<ListMobil> listMobils;
    private KatalogAdapter.onSelected listener;
    Activity context;

    public interface onSelected {
        void onPesan(ListMobil listMobil);
    }

    public KatalogAdapter(List<ListMobil> listMobils, Activity context, KatalogAdapter.onSelected listener){
        this.listMobils = listMobils;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KatalogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mobil_user, parent, false);
        KatalogAdapter.ViewHolder viewHolder = new KatalogAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final KatalogAdapter.ViewHolder holder, final int position) {
        final ListMobil listMobil = listMobils.get(position);
        holder.mNamaMobil.setText(listMobil.getNamaMobil());
        holder.mNamaMobil.setText(listMobil.getPlat());
        holder.mHarga.setText(Utils.convertRupiah(listMobil.getHarga()));
        if (listMobil.getImg() != null) {
            Picasso.get().load(App.getApplication().getString(R.string.img_url)  + listMobil.getImg()).into(holder.mFoto);
        }
        holder.mPesan.setOnClickListener(v -> {
            SweetDialogs.confirmDialog(context, "Buat pesanan sekarang ?","Klik Ya untuk lanjutkan !","", String->listener.onPesan(listMobil));
        });
    }

    @Override
    public int getItemCount() {
        if (listMobils == null)
            return 0;
        else
            return listMobils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNamaMobil, mPlat, mHarga;
        CircleImageView mFoto;
        Button mPesan;

        ViewHolder(View v){
            super(v);

            mNamaMobil = v.findViewById(R.id.mNamaMobil);
            mPlat = v.findViewById(R.id.mPlat);
            mHarga = v.findViewById(R.id.mHarga);
            mFoto = v.findViewById(R.id.mFotoMobil);
            mPesan = v.findViewById(R.id.mPesan);
        }
    }
}
