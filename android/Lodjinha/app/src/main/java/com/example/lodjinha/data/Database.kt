package com.example.alodjinha.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alodjinha.api.LodjinhaInstance
import com.example.alodjinha.data.classes.Banner
import com.example.alodjinha.data.classes.BannerList
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.data.classes.ProductList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Database {
    val call = LodjinhaInstance().lodjinhaService()

    private val dataProductList: MutableLiveData<List<Product>> = MutableLiveData()

    private val dataBannerList: MutableLiveData<List<Banner>> = MutableLiveData()
    fun insertProducts(): MutableLiveData<List<Product>> {
        call.listProduct().clone().enqueue(object : Callback<ProductList> {

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                Log.d("OnResponse","RESPONSE")
                dataProductList.postValue(response.body()?.getData())
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.d("OnFail","error" + t.message)
            }
        })

        return dataProductList
    }

    fun insertBanners(): MutableLiveData<List<Banner>> {
        call.listBanner().clone().enqueue(object : Callback<BannerList> {

            override fun onResponse(call: Call<BannerList>, response: Response<BannerList>) {
                Log.d("OnResponse","RESPONSE")
                dataBannerList.postValue(response.body()?.getData())
            }

            override fun onFailure(call: Call<BannerList>, t: Throwable) {
                Log.d("OnFail","error" + t.message)
            }
        })

        return dataBannerList
    }
}