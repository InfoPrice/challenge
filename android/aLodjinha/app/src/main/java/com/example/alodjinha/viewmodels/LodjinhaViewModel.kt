package com.example.alodjinha.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.data.managers.LodjinhaManager

class LodjinhaViewModel(val lodjinhaManager: LodjinhaManager): ViewModel() {

    private var mProducts = MutableLiveData<List<Product>>()

    fun getProducts(): LiveData<List<Product>> = mProducts

    fun loadProducts(){
        mProducts =lodjinhaManager.getProducts()
    }
}