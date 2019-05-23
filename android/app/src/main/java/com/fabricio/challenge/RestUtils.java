package com.fabricio.challenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.fabricio.challenge.control.RestService;
import com.fabricio.challenge.model.Product;
import com.fabricio.challenge.model.RestResponse;

import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestUtils {

    public static final String TAG = RestUtils.class.getSimpleName();

    static public void callRestAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://alodjinha.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        final RestService ps = retrofit.create(RestService.class);
        Call<RestResponse<Product>> createCall = ps.getAllProducts();
        createCall.enqueue(new Callback<RestResponse<Product>>() {
            @Override
            public void onResponse(Call<RestResponse<Product>> call, Response<RestResponse<Product>> response) {
                if(response != null && response.isSuccessful()) {
                    Log.d(TAG, "Products from api:");
                    try {
                        List<Product> products = response.body().getData();
                        for (Product p : products) {
                            Log.d(TAG, p.getName());
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, "Failed to get products: ", ex);
                    }
                }
            }

            @Override
            public void onFailure(Call<RestResponse<Product>> call, Throwable t) {
                Log.e(TAG, "Failed to get products from API", t);
            }
        });
    }

}
