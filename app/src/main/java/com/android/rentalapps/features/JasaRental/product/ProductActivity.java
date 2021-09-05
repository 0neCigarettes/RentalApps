package com.android.rentalapps.features.JasaRental.product;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.rentalapps.BuildConfig;
import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.product.model.ProductModel;
import com.android.rentalapps.features.JasaRental.product.presenter.ProductPresenter;
import com.android.rentalapps.features.JasaRental.product.view.IProductView;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.customer.account.ProfileUpdate.UpdateUser;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.FileUtils;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductActivity extends AppCompatActivity implements IProductView, ProductAdapter.onSelected, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.photoprofileuser)
    CircleImageView mFotoUser;
    @BindView(R.id.namaUser)
    TextView mNamaUser;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.no_item)
    LinearLayout mNoItem;
    @BindView(R.id.available_item)
    LinearLayout mAvaibleItem;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.editOrUpdate)
    LinearLayout mEditOrUpdate;
    @BindView(R.id.takephoto)
    LinearLayout mTakePhoto;
    @BindView(R.id.photoResult)
    LinearLayout mPhotoResult;
    @BindView(R.id.fotoProduct)
    ImageView mFotoProduct;
    @BindView(R.id.NamaMobil)
    TextInputEditText mNamaMobil;
    @BindView(R.id.plat)
    TextInputEditText mPlat;
    @BindView(R.id.harga)
    TextInputEditText mHarga;
    @BindView(R.id.addProduct)
    LinearLayout mAddProduct;
    @BindView(R.id.addLayout)
    LinearLayout mAddLayout;
    @BindView(R.id.add)
    Button mAdd;
    @BindView(R.id.updateLayout)
    LinearLayout mUpdateLayout;
    @BindView(R.id.update)
    Button mUpdate;
    @BindView(R.id.cacel)
    Button mCancel;

    private SweetAlertDialog sweetAlertDialog;
    private ProductAdapter adapter;
    private ProductPresenter presenter;
    private User mProfile;

    private final int CameraR_PP = 1;
    String idProduct;
    Bitmap bitmap;
    File myFile;
    String path, path2;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        ButterKnife.bind(this);

        this.initViews();
    }

    @Override
    public void initViews() {
        mProfile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );

        if (mProfile.getProfilephoto() != null){
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + mProfile.getProfilephoto()).into(mFotoUser);
        }
        mNamaUser.setText(mProfile.getFullname());

        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Sedang Memuat ...");
        sweetAlertDialog.setCancelable(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.clearFocus();
        presenter = new ProductPresenter(this);
        presenter.getListMobil(mProfile.getId());

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getListMobil(mProfile.getId());
                mSwipeRefresh.setRefreshing(false);
            }
        });

        mAdd.setOnClickListener(v -> {this.onDoAddProduct();});
        mUpdate.setOnClickListener(v -> {this.onDoUpdateProduct();});
    }

    @Override
    public void onDataReady(List<ProductModel> listProduct) {
        Log.d("product", new Gson().toJson(listProduct));
        mAddProduct.setVisibility(View.VISIBLE);
        if (listProduct.size() > 0){
            mAddProduct.setVisibility(View.VISIBLE);
            mEditOrUpdate.setVisibility(View.GONE);
            mNoItem.setVisibility(View.GONE);
            mAvaibleItem.setVisibility(View.VISIBLE);
            adapter = new ProductAdapter(this, listProduct,this);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            mEditOrUpdate.setVisibility(View.GONE);
            mNoItem.setVisibility(View.VISIBLE);
            mAvaibleItem.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        Intent i = new Intent(this, ProductActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onNetworkError(String cause) {
        Log.d("err", cause);
        SweetDialogs.endpointError(this);
    }

    @Override
    public void showLoadingIndicator() { sweetAlertDialog.show(); }

    @Override
    public void hideLoadingIndicator() { sweetAlertDialog.dismiss(); }

    @Override
    public void onAddSuccess(String mMsg) {

        SweetDialogs.commonSuccessWithIntent(this ,"Data berhasil di input !", string -> this.onRefresh());
    }

    @Override
    public void onAddFailed(String mMsg) {
        Log.d("add", mMsg);
        SweetDialogs.commonError(this, mMsg, false);
    }

    @Override
    public void onUpdateSuccess(String mMsg) {
        SweetDialogs.commonSuccessWithIntent(this ,"Data mobil telah diperbarui !", string -> this.onRefresh());
    }

    @Override
    public void onUpdateFailed(String mMsg) {
        SweetDialogs.commonError(this, mMsg, false);
    }

    @Override
    public void onDeleteSuccess(String mMsg) {
        SweetDialogs.commonSuccessWithIntent(this ,"Berhasil menghapus data !", string -> this.onRefresh());
    }

    @Override
    public void onDeleteFailed(String mMsg) {
        SweetDialogs.commonError(this, mMsg, false);
    }

    @Override
    public void onDoAddProduct() {
        ProductModel model = new ProductModel();
        model.setUserId(mProfile.getId());
        model.setNamaMobil(mNamaMobil.getText().toString());
        model.setHarga(mHarga.getText().toString());
        model.setPlat(mPlat.getText().toString());
        presenter.addProduct(model, myFile, path2);

    }

    @Override
    public void onEdit(ProductModel listProduct) {
        mAddProduct.setVisibility(View.GONE);
        mAvaibleItem.setVisibility(View.GONE);
        mNoItem.setVisibility(View.GONE);
        mEditOrUpdate.setVisibility(View.VISIBLE);
        mAddLayout.setVisibility(View.GONE);
        mUpdateLayout.setVisibility(View.VISIBLE);

        idProduct = listProduct.getId();
        mNamaMobil.setText(listProduct.getNamaMobil());
        mHarga.setText(listProduct.getHarga());
        mPlat.setText(listProduct.getPlat());
    }

    @Override
    public void onDoUpdateProduct() {
        ProductModel model = new ProductModel();
        model.setId(idProduct);
        model.setUserId(mProfile.getId());
        model.setNamaMobil(mNamaMobil.getText().toString());
        model.setHarga(mHarga.getText().toString());
        model.setPlat(mPlat.getText().toString());
        presenter.updateProduct(model, myFile, path2);
    }


    @Override
    public void onDelete(String id) {
        presenter.deleteProduct(id);
    }

    @OnClick(R.id.addProduct)
    void onAddProcut() {
        mAddProduct.setVisibility(View.GONE);
        mAvaibleItem.setVisibility(View.GONE);
        mNoItem.setVisibility(View.GONE);
        mEditOrUpdate.setVisibility(View.VISIBLE);
        mAddLayout.setVisibility(View.VISIBLE);
        mUpdateLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.cacel)
    void onCancel(){
        this.onRefresh();
    }

    @OnClick(R.id.takephoto)
    void TakeFoto(){
        addPermission();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(ProductActivity.this.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.i("Tags", "IOException");
            }
            if (photoFile != null) {
                Uri apkURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, apkURI);
                startActivityForResult(cameraIntent, CameraR_PP);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nameUser = mProfile.getFullname();
        String imageFileName = "JPEG_" + timeStamp + "_" + nameUser + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        uri = Uri.parse("file:" + image.getAbsolutePath());
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == UpdateUser.RESULT_CANCELED) {
            return;
        }
        if (requestCode == CameraR_PP) {
            try {
                path = FileUtils.getFileName(this, uri);
                myFile = new File(path);
                path2 = FileUtils.getFilePathFromURI(this, uri);
                Log.d("uri", String.valueOf(path2));
                bitmap = MediaStore.Images.Media.getBitmap(ProductActivity.this.getContentResolver(), uri);
                mFotoProduct.setImageBitmap(bitmap);
                if (mFotoProduct.getDrawable() != null) {
                    mFotoProduct.requestLayout();
                    mFotoProduct.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mFotoProduct.getLayoutParams();
                    mPhotoResult.setVisibility(View.VISIBLE);
                    mTakePhoto.setVisibility(View.GONE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                StyleableToast.makeText(ProductActivity.this, "Terjadi kesalahan...", R.style.toastStyleWarning).show();
            }
        }
    }

    @Override
    public void addPermission() {
        Dexter.withActivity(ProductActivity.this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(ProductActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}