package com.android.rentalapps.features.customer.katalog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.rentalapps.R;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.customer.katalog.ViewKatalog.IKalatalogMobilView;
import com.android.rentalapps.features.customer.katalog.model.ListMobil;
import com.android.rentalapps.features.customer.katalog.model.Order;
import com.android.rentalapps.features.customer.katalog.presenter.KatalogPresenter;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class KatalogActivity extends AppCompatActivity implements IKalatalogMobilView, KatalogAdapter.onSelected {

    @BindView(R.id.mNamaJasaRental)
    TextView mNamaJasa;
    @BindView(R.id.mAlamatJasaRental)
    TextView mAlamat;
    @BindView(R.id.photoprofileuser)
    CircleImageView mFoto;
    @BindView(R.id.mChat)
    Button mChat;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private String idJasa, namaJasa, alamat, phone, foto;

    SweetAlertDialog sweetAlertDialog;
    private KatalogPresenter presenter;
    private KatalogAdapter adapter;
    private User mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_katalog);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            idJasa = extras.getString("idJasa");
            namaJasa = extras.getString("namaJasa");
            alamat = extras.getString("alamat");
            phone = extras.getString("phone");
            foto = extras.getString("foto");

            mNamaJasa.setText(namaJasa);
            mAlamat.setText(alamat);
            if (foto != null) {
                Picasso.get().load(App.getApplication().getString(R.string.img_url) + foto).into(mFoto);
            }
            mChat.setOnClickListener(v -> this.onCall());
        }
        this.initViews();
    }

    @Override
    public void initViews() {

        mProfile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Sedang Memuat ...");
        sweetAlertDialog.setCancelable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.clearFocus();
        presenter = new KatalogPresenter(this);
        presenter.getListMobil(idJasa);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListMobil(idJasa);
                adapter.notifyDataSetChanged();
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDataReady(List<ListMobil> ListMobil) {
//        Log.d("listMobil", new Gson().toJson(ListMobil));
        adapter = new KatalogAdapter(ListMobil, this,this);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCall() {
        final String pNumber = phone.replaceFirst("0", "+62");
        String url = "https://api.whatsapp.com/send?phone=" + pNumber;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setPackage("com.whatsapp");
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onRefresh() {
        presenter.getListMobil(idJasa);
        adapter.notifyDataSetChanged();
        mSwipeRefresh.setRefreshing(false);
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
    public void onSubmitSuccses(String mMsg) {
        SweetDialogs.commonSuccessWithIntent(this ,"Persanan telah dibuat !\n Silahakan tunggu konfirmasi atau hubungi kontak yang tersedia ! ", string -> this.onRefresh());
    }

    @Override
    public void onSubmitFailed(String mMsg) {
        SweetDialogs.commonError(this, mMsg, false);
    }

    @Override
    public void onPesan(ListMobil listMobil) {
        Order order = new Order();
        order.setmCustomer_id(mProfile.getId());
        order.setmProduct_id(listMobil.getId());
        presenter.userOrder(order);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Animatoo.animateSlideUp(this);
    }
}