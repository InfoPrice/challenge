package com.example.alodjinha.api

import com.example.alodjinha.api.interfaces.GetLodjinhaDataService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LodjinhaInstance {
    private var retrofit: Retrofit? = null
    fun getOmdbInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://alodjinha.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun lodjinhaService()  = getOmdbInstance().create(GetLodjinhaDataService::class.java)
}