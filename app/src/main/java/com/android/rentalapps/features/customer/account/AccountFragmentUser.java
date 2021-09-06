package com.android.rentalapps.features.customer.account;

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
import com.android.rentalapps.features.auth.Login;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.customer.account.ProfileUpdate.UpdateUser;
import com.android.rentalapps.features.customer.account.view.IAccountUserView;
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
public class AccountFragmentUser extends Fragment implements IAccountUserView {

    @BindView(R.id.dfullname)
    TextView mFullname;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.email)
    TextView mEmail;
    @BindView(R.id.showData)
    TextView mLihatData;
    @BindView(R.id.logout)
    LinearLayout mLogout;
    @BindView(R.id.images)
    LottieAnimationView mDefaultPhoto;
    @BindView(R.id.photoprofileuser)
    CircleImageView mProfileFoto;
    @BindView(R.id.view1)
    ImageView mBackgroundProfile;
    @BindView(R.id.customerCare)
    LinearLayout mCustomerCare;

    User mProfile ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account_user, container, false);
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

        mFullname.setText(mProfile.getFullname());
        mPhone.setText(mProfile.getPhone());
        mAddress.setText(mProfile.getAddress());
        mEmail.setText(mProfile.getEmail());

        if (mProfile.getProfilephoto() == null){
            mBackgroundProfile.setVisibility(View.VISIBLE);
            mProfileFoto.setVisibility(View.GONE);
        } else{
            mDefaultPhoto.cancelAnimation();
            mProfileFoto.setVisibility(View.VISIBLE);
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + mProfile.getProfilephoto()).into(mProfileFoto);
            Picasso.get().load(App.getApplication().getString(R.string.img_url) + mProfile.getProfilephoto()).into(mBackgroundProfile);
        }

    }

    @OnClick(R.id.customerCare)
    void onCustomerCare(){
        final String phoneD = "082279058667";
        final String str1 = phoneD.replaceFirst("0", "+62");
        String url = "https://api.whatsapp.com/send?phone=" + str1;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setPackage("com.whatsapp");
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.showData)
    void onShowData() {
        startActivity(new Intent(getActivity(), UpdateUser.class));
        Animatoo.animateSlideUp(getActivity());
    }

    @OnClick(R.id.logout)
    void doLogout() {
        App.getPref().clear();
        startActivity(new Intent(getActivity(), Login.class));
        Animatoo.animateSlideUp(getActivity());
    }
}
