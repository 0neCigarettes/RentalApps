package com.android.rentalapps.features.customer.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.android.rentalapps.ui.SweetDialogs;
import com.android.rentalapps.features.customer.home.Presenter.UserPresenter;
import com.android.rentalapps.features.customer.home.ViewHomeUser.IHomeUserView;
import com.android.rentalapps.features.customer.home.Model.ListJasaModel;
import com.android.rentalapps.features.customer.katalog.KatalogActivity;
import com.android.rentalapps.features.customer.order.OrderActivity;
import com.android.rentalapps.utils.CustomDrawable;
import com.android.rentalapps.utils.Utils;
import com.android.rentalapps.R;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.utils.App;
import com.android.rentalapps.utils.GsonHelper;
import com.android.rentalapps.utils.Prefs;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragmentUser extends Fragment implements IHomeUserView {

    @BindView(R.id.mOrderFab)
    FloatingActionButton mOrderFab;
    @BindView(R.id.find)
    Button findData;
    private User mProfile;

    private SupportMapFragment supportMapFragment;
    List<ListJasaModel> mListMarker = new ArrayList<>();
    List<String> mTitles = new ArrayList<>();
    private LatLng myLocation = new LatLng(0, 0);
    private HashMap<String, HashMap<String, String>> dataInfo = new HashMap<String, HashMap<String, String>>();
    private UserPresenter presenter;
    SweetAlertDialog sweetAlertDialog;
    GoogleMap mMaps;
    Marker marker, myLocationMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home_user, container, false);
        ButterKnife.bind(this, v);
        this.initViews();
        presenter = new UserPresenter(this);
        presenter.getListJasa();

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps_google);
        supportMapFragment.getMapAsync(this::onMapReady);

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return v;
    }

    @Override
    public void initViews() {
        mProfile = (User) GsonHelper.parseGson(
                App.getPref().getString(Prefs.PREF_STORE_PROFILE, ""),
                new User()
        );
        sweetAlertDialog = new SweetAlertDialog(requireContext(),SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Sedang Memuat ...");
        sweetAlertDialog.setCancelable(false);

        findData.setVisibility(View.GONE);
        findData.setOnClickListener(v -> {this.onGetListJasa();});

        mOrderFab.setImageDrawable(CustomDrawable.googleMaterialDrawable(
                getContext(), R.color.color_default_blue, 24, GoogleMaterial.Icon.gmd_shopping_cart
        ));
        mOrderFab.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), OrderActivity.class);
            startActivity(i);
            Animatoo.animateSlideRight(getActivity());
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDataReady(List<ListJasaModel> ListJasa) {
        mListMarker = ListJasa;
        int height = 120;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_mobil);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        if (mTitles.size() == 0 && myLocation != null) {
            for (int i = 0; i < mListMarker.size(); i++) {
                mTitles.add(mListMarker.get(i).getFullname().split("\\|")[0]);
                LatLng location = new LatLng(Double.parseDouble(mListMarker.get(i).getLati()), Double.parseDouble(mListMarker.get(i).getLongi()));
                marker = mMaps.addMarker(
                        new MarkerOptions()
                                .position(location)
                                .title(mTitles.get(i))
                                .snippet(mListMarker.get(i).getAddress())
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("jasa_id",mListMarker.get(i).getId());
                data.put("namaJasa",mListMarker.get(i).getFullname());
                data.put("alamat",mListMarker.get(i).getAddress());
                data.put("phone",mListMarker.get(i).getPhone());
                data.put("foto", mListMarker.get(i).getProfilephoto());
                dataInfo.put(marker.getId(), data);
                CameraPosition cmr = CameraPosition.builder().target(new LatLng(location.latitude, location.longitude)).zoom(12).bearing(0).tilt(45).build();
                mMaps.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude, location.longitude), 11.0f));
                mMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMaps.moveCamera(CameraUpdateFactory.newCameraPosition(cmr));

                mMaps.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        if (!marker.getTitle().equals("Lokasi Saya")){
                            HashMap<String, String> marker_data = dataInfo.get(marker.getId());
                            Intent i = new Intent(getActivity(), KatalogActivity.class);
                            i.putExtra("idJasa", marker_data.get("jasa_id"));
                            i.putExtra("namaJasa", marker_data.get("namaJasa"));
                            i.putExtra("alamat", marker_data.get("alamat"));
                            i.putExtra("phone", marker_data.get("phone"));
                            i.putExtra("foto", marker_data.get("foto"));
                            startActivity(i);
                            Animatoo.animateSlideDown(getContext());
                        } else {
                            Toast.makeText(getContext(), ""+marker.getTitle(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            HashSet<String> hashSet = new HashSet<>();
            hashSet.addAll(mTitles);
            mTitles.clear();
            mTitles.addAll(hashSet);
        } else {
            setMyLocation();
        }
    }

    @Override
    public void onNetworkError(String cause) {
        Log.d("errornya", cause);
        SweetDialogs.endpointError(getActivity());

    }

    @Override
    public void onGetListJasa() {
        Toast.makeText(getActivity(), "adasdsadsad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator() {sweetAlertDialog.show();}

    @Override
    public void hideLoadingIndicator() {sweetAlertDialog.dismiss();}

    @Override
    public void setMyLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMaps.setMyLocationEnabled(true);
        mMaps.getUiSettings().setMyLocationButtonEnabled(true);
        mMaps.getUiSettings().setZoomControlsEnabled(true);
        mMaps.setMinZoomPreference(8.0f);
        mMaps.setMaxZoomPreference(20.0f);
        mMaps.setPadding(0, 100, 0, 150);
        mMaps.setOnMyLocationChangeListener(location -> {
            if (location.getLatitude() != 0 && location.getLongitude() != 0) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                myLocation = latLng;
                if (myLocationMarker == null) {
                    initMyLocationMarker();
                } else myLocationMarker.setPosition(myLocation);
            }
        });
    }

    @Override
    public void initMyLocationMarker() {
        Drawable d = CustomDrawable.googleMaterialDrawable(
                getActivity(), R.color.red, 24, GoogleMaterial.Icon.gmd_person_pin);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(Utils.drawableToBitmap(d));
        if (mMaps != null) {
            myLocationMarker = mMaps.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .title("Lokasi Saya")
                    .icon(icon));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMaps = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMaps.setMyLocationEnabled(true);
        mMaps.getUiSettings().setMyLocationButtonEnabled(true);
        mMaps.setOnMyLocationChangeListener(location -> {
            if (location.getLatitude() != 0 && location.getLongitude() != 0) {
                LatLng lng = new LatLng(location.getLatitude(), location.getLongitude());
                myLocation = lng;
                if (myLocationMarker == null) {
                    setMyLocation();
                } else myLocationMarker.setPosition(myLocation);
            }
        });
    }
}
