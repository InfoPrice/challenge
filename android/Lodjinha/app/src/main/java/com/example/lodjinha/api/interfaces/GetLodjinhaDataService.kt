package com.example.alodjinha.api.interfaces

import com.example.alodjinha.data.classes.BannerList
import com.example.alodjinha.data.classes.ProductList
import retrofit2.Call
import retrofit2.http.GET

interface GetLodjinhaDataService {

    @GET("produto/maisvendidos")
    fun listProduct(): Call<ProductList>

    @GET("banner")
    fun listBanner(): Call<BannerList>
}