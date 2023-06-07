package com.example.alodjinha.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alodjinha.data.classes.Banner
import com.example.alodjinha.data.classes.Category
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.data.managers.LodjinhaManager

class LodjinhaViewModel(val lodjinhaManager: LodjinhaManager): ViewModel() {

    private var mProducts = MutableLiveData<List<Product>>()
    private var mBanners = MutableLiveData<List<Banner>>()
    private var mCategorys = MutableLiveData<List<Category>>()
    private var mAllProducts = MutableLiveData<List<Product>>()
    fun getProducts(): LiveData<List<Product>> = mProducts

    fun loadProducts(){
        mProducts =lodjinhaManager.getProducts()
    }

    fun getBanners(): LiveData<List<Banner>> = mBanners

    fun loadBanners(){
        mBanners = lodjinhaManager.getBanners()
    }

    fun loadCategory(){
        mCategorys = lodjinhaManager.getCategory()
    }

    fun getCategorys(): LiveData<List<Category>> = mCategorys

    fun getAllProducts(): LiveData<List<Product>> = mAllProducts

    fun loadAllProducts(offset: Int, limit: Int, categoriaId: Int){
        mAllProducts =lodjinhaManager.getAllProducts(offset,limit,categoriaId)
    }
}