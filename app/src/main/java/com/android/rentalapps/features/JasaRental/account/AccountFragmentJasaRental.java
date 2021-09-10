package com.android.rentalapps.features.JasaRental.account;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.android.rentalapps.R;
import com.android.rentalapps.features.JasaRental.account.profile.UpdateJasaRental;
import com.android.rentalapps.features.JasaRental.account.view.IAccountJasaView;
import com.android.rentalapps.features.auth.login.Login;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AccountFragmentJasaRental extends Fragment implements IAccountJasaView {

    @BindView(R.id.dfullname)
    TextView dFullname;
    @BindView(R.id.phone)
    TextView dPhone;
    @BindView(R.id.address)
    TextView dAddress;
    @BindView(R.id.email)
    TextView mEmail;
    @BindView(R.id.lengkapiDriver)
    TextView dLengkapi;
    @BindView(R.id.showDataDriver)
    TextView dLihat;

    @BindView(R.id.logout)
    LinearLayout bLogout;
    @BindView(R.id.images)
    LottieAnimationView defaultPhoto;
    @BindView(R.id.photoprofileuser)
    CircleImageView profilePhotoUser;

    @BindView(R.id.view1)
    ImageView backgroundProfile;
    @BindView(R.id.customerCare)
    LinearLayout customer;

    User mProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_jasa_rental, container, false);
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

        dFullname.setText(mProfile.getFullname());
        dPhone.setText(mProfile.getPhone());
        dAddress.setText(mProfile.getAddress());
        mEmail.setText(mProfile.getEmail());

        if (mProfile.getProfilephoto() == null || mProfile.getAddress() == null) {
            backgroundProfile.setVisibility(View.VISIBLE);
            profilePhotoUser.setVisibility(View.GONE);
        } else {
            defaultPhoto.cancelAnimation();
            profilePhotoUser.setVisibility(View.VISIBLE);
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + mProfile.getProfilephoto()).into(profilePhotoUser);
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + mProfile.getProfilephoto()).into(backgroundProfile);
        }

        dLihat.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), UpdateJasaRental.class));
            Animatoo.animateSlideUp(getActivity());
        });

    }

    @OnClick(R.id.customerCare)
    void onCall() {
        final String phoneD = App.getApplication().getString(R.string.nomorAdmin);
        final String str1 = phoneD.replaceFirst("0", "+62");
        String url = "https://api.whatsapp.com/send?phone=" + str1;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setPackage("com.whatsapp");
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.logout)
    void doLogout() {
        App.getPref().clear();
        startActivity(new Intent(getActivity(), Login.class));
        Animatoo.animateSlideUp(getActivity());
    }
}
