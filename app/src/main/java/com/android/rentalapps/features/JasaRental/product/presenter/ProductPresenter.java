package com.android.rentalapps.features.JasaRental.product.presenter;

import android.util.Log;

import com.android.rentalapps.features.JasaRental.product.model.ProductModel;
import com.android.rentalapps.features.JasaRental.product.model.ProductResponse;
import com.android.rentalapps.features.JasaRental.product.view.IProductView;
import com.android.rentalapps.model.CommonResponse;
import com.android.rentalapps.server.NetworkService;
import com.android.rentalapps.server.RestService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductPresenter {
    final IProductView view;
    public final Retrofit restService;

    public ProductPresenter(IProductView view) {
        this.view = view;
        restService = RestService.getRetroftInstance();
    }

    public void getListMobil(String id) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).getProduct(id)
                .enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus())
                            view.onDataReady(response.body().getmResult());
                    }

                    @Override
                    public void onFailure(Call<ProductResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }

    public void addProduct(ProductModel model, File myImg, String path) {
        view.showLoadingIndicator();
        MultipartBody.Part img;
        RequestBody requestFile;
        if (myImg != null) {
            File file = new File(path);
            requestFile =  RequestBody.create(MediaType.parse("multipart/form-data"), file);
            img = MultipartBody.Part.createFormData("img", myImg.getName(), requestFile);
        } else {
            img = null;
            Log.d("img", "kosong");
        }
        restService.create(NetworkService.class).addProduct(img, model)
                .enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus()) {
                            view.onAddSuccess(response.body().getmMsg());
                        } else {
                            view.onAddFailed(response.body().getmMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }

    public void updateProduct(ProductModel model, File myImg, String path) {
        view.showLoadingIndicator();
        MultipartBody.Part img;
        RequestBody requestFile;
        if (myImg != null) {
            File file = new File(path);
            requestFile =  RequestBody.create(MediaType.parse("multipart/form-data"), file);
            img = MultipartBody.Part.createFormData("img", myImg.getName(), requestFile);
        } else {
            img = null;
            Log.d("img", "kosong");
        }
        restService.create(NetworkService.class).updateProduct(img, model)
                .enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus()) {
                            view.onUpdateSuccess(response.body().getmMsg());
                        } else {
                            view.onUpdateFailed(response.body().getmMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }

    public void deleteProduct(String id) {
        view.showLoadingIndicator();
        restService.create(NetworkService.class).deleteProduct(id)
                .enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        view.hideLoadingIndicator();
                        if (response.body().getmStatus()) {
                            view.onDeleteSuccess(response.body().getmMsg());
                        } else {
                            view.onDeleteFailed(response.body().getmMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {
                        view.hideLoadingIndicator();
                        view.onNetworkError(t.getLocalizedMessage());
                    }
                });
    }
}
