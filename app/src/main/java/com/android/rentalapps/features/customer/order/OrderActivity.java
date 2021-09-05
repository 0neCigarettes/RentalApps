package com.android.rentalapps.features.customer.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.rentalapps.R;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.customer.order.model.MyOrder;
import com.android.rentalapps.features.customer.order.presenter.OrderPresenter;
import com.android.rentalapps.features.customer.order.view.IOrderView;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderActivity extends AppCompatActivity implements IOrderView, OrderAdapter.onSelected {

    @BindView(R.id.mFotoUser)
    CircleImageView mFotoUser;
    @BindView(R.id.mNamaUser)
    TextView mNamaUser;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.no_item)
    LinearLayout mNo_item;
    @BindView(R.id.available_item)
    LinearLayout mAvaible_item;

    SweetAlertDialog sweetAlertDialog;
    private OrderPresenter presenter;
    private OrderAdapter adapter;
    private User mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        this.initViews();
    }

    @Override
    public void initViews() {
        mProfile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );

        mNamaUser.setText("Hi, " + mProfile.getFullname());
        Picasso.get().load(App.getApplication().getString(R.string.img_url)  + mProfile.getProfilephoto()).into(mFotoUser);

        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Sedang Memuat ...");
        sweetAlertDialog.setCancelable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.clearFocus();
        presenter = new OrderPresenter(this);
        presenter.getMyOrder(mProfile.getId());

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getMyOrder(mProfile.getId());
                adapter.notifyDataSetChanged();
                mSwipeRefresh.setRefreshing(false);
            }
        });

    }

    @Override
    public void onDataReady(List<MyOrder> listOrder) {
        Log.d("res", new Gson().toJson(listOrder));
        if(listOrder.size() > 0) {
            adapter = new OrderAdapter(listOrder, this, this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            mNo_item.setVisibility(View.VISIBLE);
            mAvaible_item.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNetworkError(String cause) {
        Log.d("Error", cause);
        SweetDialogs.endpointError(this);
    }

    @Override
    public void showLoadingIndicator() { sweetAlertDialog.show(); }

    @Override
    public void hideLoadingIndicator() { sweetAlertDialog.dismiss(); }

    @Override
    public void onCall(MyOrder myOrder) {
        final String pNumber = myOrder.getmPhoneJasa().replaceFirst("0", "+62");
        String url = "https://api.whatsapp.com/send?phone=" + pNumber;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setPackage("com.whatsapp");
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        finish();
        Animatoo.animateSlideLeft(this);
    }
}