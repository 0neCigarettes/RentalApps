package com.android.rentalapps.features.JasaRental.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.home.model.OrderJasa;
import com.android.rentalapps.features.JasaRental.home.presenter.HomeJasaPresenter;
import com.android.rentalapps.features.JasaRental.home.view.IHomeJasaView;
import com.android.rentalapps.features.JasaRental.product.ProductActivity;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.ui.BottomDialogs;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.google.gson.Gson;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragmentJasaRental extends Fragment implements IHomeJasaView, HomeJasaAdapter.onSelected {


    @BindView(R.id.photoprofileuser)
    CircleImageView mPhotoprofileuser;
    @BindView(R.id.namaUser)
    TextView mNamaUser;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_item)
    LinearLayout mNoItem;
    @BindView(R.id.available_item)
    LinearLayout mAvaibleItem;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    SweetAlertDialog sweetAlertDialog;
    User mProfile;
    private HomeJasaPresenter presenter;
    private HomeJasaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_jasa_rental, container, false);
        ButterKnife.bind(this, v);
        this.initViews();
        return v;
    }

    @Override
    public void initViews() {
        mProfile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );

        mNamaUser.setText(mProfile.getFullname());
        if (mProfile.getProfilephoto() != null){
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + mProfile.getProfilephoto()).into(mPhotoprofileuser);
        }
        if (mProfile.getStatus().equals("on")) {
            mStatus.setText("Status akun aktif");
        } else {
            mStatus.setText("Status akun belum aktif");
        }
        sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Sedang memuat...");
        sweetAlertDialog.setCancelable(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.clearFocus();

        presenter = new HomeJasaPresenter(this);
        presenter.getOrders(mProfile.getId());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getOrders(mProfile.getId());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDataReady(List<OrderJasa> listOrder) {
        Log.d("order", new Gson().toJson(listOrder));
        if (listOrder.size() > 0){
            mAvaibleItem.setVisibility(View.VISIBLE);
            mNoItem.setVisibility(View.GONE);
            adapter = new HomeJasaAdapter(listOrder, getActivity(),this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            mAvaibleItem.setVisibility(View.GONE);
            mNoItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNetworkError(String cause) {
        Log.d("err", cause);
        SweetDialogs.endpointError(getActivity());
    }

    @Override
    public void onUpdateSuccess(String mMsg) {
        SweetDialogs.commonWarningWithIntent(getActivity(),"Berhasil merubah status","", String->onRefresh());
    }

    @Override
    public void onUpdateFailed(String mMsg) {
        SweetDialogs.commonWarningWithIntent(getActivity(),"Gagal merubah status","", String->onRefresh());
    }

    @Override
    public void showLoadingIndicator() {
        sweetAlertDialog.show();
    }

    @Override
    public void hideLoadingIndicator() {
        sweetAlertDialog.dismiss();
    }

    @Override
    public void onRefresh() {
        presenter.getOrders(mProfile.getId());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAcc(OrderJasa data) {
        presenter.updateStatusOrder(data.getmId(), "2");
    }

    @Override
    public void onDone(OrderJasa data) {
        presenter.updateStatusOrder(data.getmId(), "3");
    }

    @Override
    public void onTolak(OrderJasa data) {
        presenter.updateStatusOrder(data.getmId(), "3");
    }

    @Override
    public void onCek(OrderJasa data) {
        if (data != null){
            BottomDialogs.showPesananDialog(getActivity(), "ini", data);
        }
    }

    @OnClick(R.id.productList)
    void onProduct(){
        Intent i = new Intent(getActivity(), ProductActivity.class);
        startActivity(i);
    }
}
