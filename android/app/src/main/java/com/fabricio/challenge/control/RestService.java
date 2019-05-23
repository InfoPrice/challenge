package com.fabricio.challenge.control;


import com.fabricio.challenge.model.Banner;
import com.fabricio.challenge.model.Category;
import com.fabricio.challenge.model.Product;
import com.fabricio.challenge.model.RestResponse;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestService {

    @GET("banner")
    Call<RestResponse<Banner>> getAllBanners();

    @GET("categoria")
    Call<RestResponse<Category>> getAllCategories();

    @GET("produto")
    Call<RestResponse<Product>> getAllProducts();

    @GET("produto/maisvendidos")
    Call<RestResponse<Product>> getBestSellers();

    @GET("produto/{product_id}")
    Call<Product> getProductById(@Path("product_id") Integer productId);

    @POST("produto/{product_id}")
    Call markProduct(@NonNull @Path("product_id") Integer productId); // TODO check if it is Path or Query in the annotation

}
