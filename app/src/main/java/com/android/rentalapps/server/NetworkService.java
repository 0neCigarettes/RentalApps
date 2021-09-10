package com.android.rentalapps.server;

import com.android.rentalapps.features.JasaRental.home.model.OrderResponse;
import com.android.rentalapps.features.JasaRental.product.model.ProductModel;
import com.android.rentalapps.features.JasaRental.product.model.ProductResponse;
import com.android.rentalapps.features.auth.model.LoginResponse;
import com.android.rentalapps.features.auth.model.User;
import com.android.rentalapps.features.customer.home.Model.ListJasaResponse;
import com.android.rentalapps.features.customer.katalog.model.ListMobilResponse;
import com.android.rentalapps.features.customer.katalog.model.Order;
import com.android.rentalapps.features.customer.order.model.MyOrderResponse;
import com.android.rentalapps.model.CommonResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NetworkService {

    //auth
    @POST("login")
    Call<LoginResponse> signin(@Body User model);

    @POST("regis")
    Call<CommonResponse> signup(@Body User model);

    //jasa
    @GET("jasa/product/{id}")
    Call<ProductResponse> getProduct(@Path("id") String id);

    @Multipart
    @POST("jasa/addproduct")
    Call<CommonResponse> addProduct(@Part MultipartBody.Part file, @Part("data")ProductModel model);

    @Multipart
    @POST("jasa/updateproduct")
    Call<CommonResponse> updateProduct(@Part MultipartBody.Part file, @Part("data")ProductModel model);

    @POST("jasa/deleteproduct/{product_id}")
    Call<CommonResponse> deleteProduct(@Path("product_id") String product_id);

    @GET("jasa/order/{id}")
    Call<OrderResponse> getOrders(@Path("id") String id);

    @POST("jasa/order/changestatus/{id}/{key}")
    Call<CommonResponse> updateStatusOrder(@Path("id") String id, @Path("key") String key);

    //user
    @GET("users/getalljasa")
    Call<ListJasaResponse> getListJasa();

    @GET("users/getproductjasa/{id}")
    Call<ListMobilResponse> getListMobil(@Path("id") String id);

    @POST("users/order")
    Call<CommonResponse> userOrder(@Body Order userOrder);

    @GET("users/getmyorder/{id}")
    Call<MyOrderResponse> getMyOrder(@Path("id") String id);

    @Multipart
    @POST("users/updateprofile")
    Call<LoginResponse> profile(@Part MultipartBody.Part file, @Part("data") User model);
}
