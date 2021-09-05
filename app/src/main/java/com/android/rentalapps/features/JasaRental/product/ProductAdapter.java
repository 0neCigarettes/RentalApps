package com.android.rentalapps.features.JasaRental.product;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.product.model.ProductModel;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Activity context;
    public List<ProductModel> ListProduct;
    private ProductAdapter.onSelected listener;

    public ProductAdapter(Activity context, List<ProductModel> ListProduct, ProductAdapter.onSelected listener) {
        this.context = context;
        this.ListProduct = ListProduct;
        this.listener = listener;
    }

    public interface onSelected {
        void onEdit(ProductModel listProduct);
        void onDelete(String id);

    }
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent, false);
        ProductAdapter.ViewHolder viewHolder = new ProductAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel m = ListProduct.get(position);

        holder.idProduct = m.getId();
        holder.namaMobil.setText(m.getNamaMobil());
        holder.plat.setText(m.getPlat());
        holder.harga.setText(Utils.convertRupiah(m.getHarga()));
        if (m.getmImg() != null) {
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + m.getmImg()).into(holder.mFotoMobil);
        }

        holder.mEdit.setOnClickListener(V -> listener.onEdit(m));
        holder.mHapus.setOnClickListener(V -> {
            SweetDialogs.confirmDialog(context, "Hapus data mobil ini ?","Klik Ya untuk lanjutkan !","", String->listener.onDelete(m.getId()));
        });


    }

    @Override
    public int getItemCount() {
        return ListProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button mEdit, mHapus;
        String idProduct;
        TextView namaMobil, plat, harga;
        CircleImageView mFotoMobil;
        public ViewHolder(@NonNull View v) {
            super(v);

            namaMobil = v.findViewById(R.id.namaMobil);
            plat = v.findViewById(R.id.plat);
            harga = v.findViewById(R.id.harga);
            mFotoMobil = v.findViewById(R.id.fotoMobil);
            mEdit = v.findViewById(R.id.edit);
            mHapus = v.findViewById(R.id.hapus);
        }
    }

}

