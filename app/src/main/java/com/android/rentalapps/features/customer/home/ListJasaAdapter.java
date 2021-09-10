package com.android.rentalapps.features.customer.home;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rentalapps.R;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.utils.App;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListJasaAdapter extends RecyclerView.Adapter<ListJasaAdapter.ViewHolder> {

    private Context context;
    private List<User> listJasa;
    private ListJasaAdapter.onSelected listener;

    public interface onSelected{
        void onSelect(User data);
    }

    public ListJasaAdapter(List<User> listJasa, Context context, ListJasaAdapter.onSelected listener){
        this.context = context;
        this.listJasa = listJasa;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListJasaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.list_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListJasaAdapter.ViewHolder holder, final int position) {
        final User jasa = listJasa.get(position);
        holder.mItem.setOnClickListener(v -> { listener.onSelect(jasa);});
        holder.mFullname.setText(jasa.getFullname());
        String ditsance = jasa.getDistance() + " KM</b><br>dari Lokasi Anda";
        holder.mDistance.setText(Html.fromHtml(ditsance));
        holder.mPhone.setText(jasa.getPhone());
        holder.mAlamat.setText(jasa.getAddress());
        if (jasa.getProfilephoto() != null) {
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + jasa.getProfilephoto()).into(holder.mFoto);
        }
    }

    @Override
    public int getItemCount() {
        if (listJasa == null)
            return 0;
        else
            return listJasa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mItem;
        CircleImageView mFoto;
        TextView mFullname, mDistance, mPhone, mAlamat;

        public ViewHolder(@NonNull View v) {
            super(v);
            mItem = v.findViewById(R.id.mItem);
            mFoto = v.findViewById(R.id.mFoto);
            mFullname = v.findViewById(R.id.fullname);
            mDistance = v.findViewById(R.id.distance);
            mPhone = v.findViewById(R.id.phone);
            mAlamat = v.findViewById(R.id.alamat);
        }
    }
}
