package com.example.alodjinha.api.interfaces

import com.example.alodjinha.data.classes.BannerList
import com.example.alodjinha.data.classes.ProductList
import com.example.lodjinha.data.classes.CategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetLodjinhaDataService {

    @GET("produto/maisvendidos")
    fun listProduct(): Call<ProductList>

    @GET("banner")
    fun listBanner(): Call<BannerList>

    @GET("categoria")
    fun listCategory(): Call<CategoryList>

    @GET("produto/")
    fun listProductsAll(@Query("offset") offset: Int,
                        @Query("limit") limit: Int ,
                        @Query("categoriaId") categoriaId: Int ): Call<ProductList>
}