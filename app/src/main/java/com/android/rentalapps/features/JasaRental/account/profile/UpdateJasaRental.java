package com.android.rentalapps.features.JasaRental.account.profile;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.android.rentalapps.BuildConfig;
import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.account.profile.presenter.ProfileJasaPresenter;
import com.android.rentalapps.features.JasaRental.account.profile.view.IUpdateJasaView;
import com.android.rentalapps.features.JasaRental.home.MainJasaRental;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.FileUtils;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputEditText;
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
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateJasaRental extends AppCompatActivity implements IUpdateJasaView {

    @BindView(R.id.dfullname)
    TextView mFullname;
    @BindView(R.id.NamaJasaRental)
    TextInputEditText mFullnameUser;
    @BindView(R.id.phone)
    TextInputEditText mPhone;
    @BindView(R.id.email)
    TextInputEditText mEmail;
    @BindView(R.id.adressuser)
    TextInputEditText mAddress;
    @BindView(R.id.profilephotouser)
    ImageView mProfilephoto;
    @BindView(R.id.photoResult)
    LinearLayout mPhotoresult;
    @BindView(R.id.takephoto)
    LinearLayout mTakeFoto;
    @BindView(R.id.do_update)
    Button mUpdate;
    @BindView(R.id.images)
    LottieAnimationView mDefaultPhoto;
    @BindView(R.id.photoprofileuser)
    CircleImageView mFotoProfile;
    @BindView(R.id.view1)
    ImageView mBackgroundProfile;

    private SweetAlertDialog sweetAlertDialog;
    private final int CameraR_PP = 1;
    private ProfileJasaPresenter presenter;
    private User mProfile;
    Bitmap bitmap;
    File myFile;
    String path, path2;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_jasa_rental);
        ButterKnife.bind(this);
        this.initViews();
    }

    @Override
    public void initViews() {
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Sedang Memuat ...");
        sweetAlertDialog.setCancelable(false);
        presenter = new ProfileJasaPresenter(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mProfile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );

        mTakeFoto.setOnClickListener(v -> {this.takePhotoNow();});

        if (mProfile.getProfilephoto() == null) {
            mBackgroundProfile.setVisibility(View.GONE);
            mFotoProfile.setVisibility(View.GONE);
        } else {
            mDefaultPhoto.cancelAnimation();
            mFotoProfile.setVisibility(View.VISIBLE);
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + mProfile.getProfilephoto()).into(mFotoProfile);
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + mProfile.getProfilephoto()).into(mBackgroundProfile);
        }

        mFullname.setText(mProfile.getFullname());
        mFullnameUser.setText(mProfile.getFullname());
        mPhone.setText(mProfile.getPhone());
        mEmail.setText(mProfile.getEmail());
        mAddress.setText(mProfile.getAddress());

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mUpdate.setOnClickListener(v -> { this.onSubmit();});
    }

    @Override
    public void onSubmit() {
        User model = new User();
        model.setId(mProfile.getId());
        model.setFullname(mFullnameUser.getText().toString());
        model.setEmail(mEmail.getText().toString());
        model.setPhone(mPhone.getText().toString());
        model.setAddress(mAddress.getText().toString());
        presenter.profileUpdate(model, myFile, path2);
    }

    @Override
    public void onNetworkError(String cause) {
        Log.d("Error", cause);
        SweetDialogs.endpointError(this);
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
    public void onSubmitSuccses(String mMsg) {
        SweetDialogs.commonSuccessWithIntent(this ,"Data anda telah diperbarui !", string -> this.onGoToDashboard());
    }

    @Override
    public void onSubmitFailed(String mMsg) {
        SweetDialogs.commonError(this, mMsg, false);
    }

    @Override
    public void onGoToDashboard() {
        Intent i = new Intent(this, MainJasaRental.class);
        startActivity(i);
        Animatoo.animateSlideDown(this);
    }

    private void takePhotoNow() {
        addPermission();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
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

        if (resultCode == UpdateJasaRental.RESULT_CANCELED) {
            return;
        }

        if (requestCode == CameraR_PP) {
            try {
                path = FileUtils.getFileName(this, uri);
                myFile = new File(path);
                path2 = FileUtils.getFilePathFromURI(this, uri);
                Log.d("uri", String.valueOf(path2));
                bitmap = MediaStore.Images.Media.getBitmap(UpdateJasaRental.this.getContentResolver(), uri);
                mProfilephoto.setImageBitmap(bitmap);
                if (mProfilephoto.getDrawable() != null) {
                    mProfilephoto.requestLayout();
                    mProfilephoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mProfilephoto.getLayoutParams();
                    mPhotoresult.setVisibility(View.VISIBLE);
                    mTakeFoto.setVisibility(View.GONE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                StyleableToast.makeText(UpdateJasaRental.this, "Terjadi kesalahan...", R.style.toastStyleWarning).show();
            }
        }
    }

    @Override
    public void addPermission() {
        Dexter.withActivity(UpdateJasaRental.this)
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
                        Toast.makeText(UpdateJasaRental.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateJasaRental.this, MainJasaRental.class));
        Animatoo.animateSlideDown(UpdateJasaRental.this);
    }
}
