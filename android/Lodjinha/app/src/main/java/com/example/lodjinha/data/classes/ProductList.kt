package com.example.alodjinha.data.classes

import com.example.alodjinha.data.classes.Product
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductList {

    @SerializedName("data")
    @Expose
    private var data: List<Product>? = null

    fun setData(Data: List<Product>){
        data = Data
    }

    fun getData() = data
}