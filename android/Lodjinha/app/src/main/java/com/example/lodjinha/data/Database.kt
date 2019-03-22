package com.example.alodjinha.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alodjinha.api.LodjinhaInstance
import com.example.alodjinha.data.classes.*
import com.example.lodjinha.data.classes.CategoryList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Database {
    val call = LodjinhaInstance().lodjinhaService()

    private val dataProductList: MutableLiveData<List<Product>> = MutableLiveData()
    private val dataProductsAllList: MutableLiveData<List<Product>> = MutableLiveData()
    private val dataCategoryList: MutableLiveData<List<Category>> = MutableLiveData()
    private val dataBannerList: MutableLiveData<List<Banner>> = MutableLiveData()
    fun insertProducts(): MutableLiveData<List<Product>> {
        call.listProduct().clone().enqueue(object : Callback<ProductList> {

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                Log.d("OnResponse","RESPONSEPRODUCT")
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
                Log.d("OnResponse","RESPONSEBANNER")
                dataBannerList.postValue(response.body()?.getData())
            }

            override fun onFailure(call: Call<BannerList>, t: Throwable) {
                Log.d("OnFail","error" + t.message)
            }
        })

        return dataBannerList
    }

    fun insertCategory(): MutableLiveData<List<Category>> {
        call.listCategory().clone().enqueue(object : Callback<CategoryList> {

            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                Log.d("OnResponse","RESPONSECATEGORY")
                dataCategoryList.postValue(response.body()?.getData())
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("OnFail","error" + t.message)
            }
        })

        return dataCategoryList
    }

    fun insertAllProducts(offset: Int, limit: Int, categoriaId: Int): MutableLiveData<List<Product>> {
        call.listProductsAll(offset, limit, categoriaId).clone().enqueue(object : Callback<ProductList> {

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                Log.d("OnResponse","RESPONSE")
                dataProductsAllList.postValue(response.body()?.getData())
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.d("OnFail","error" + t.message)
            }
        })

        return dataProductsAllList
    }
}