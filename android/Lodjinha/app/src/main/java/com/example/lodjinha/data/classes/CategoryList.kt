package com.example.lodjinha.data.classes

import com.example.alodjinha.data.classes.Banner
import com.example.alodjinha.data.classes.Category
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryList {

    @SerializedName("data")
    @Expose
    private var data: List<Category>? = null

    fun setData(Data: List<Category>){
        data = Data
    }

    fun getData() = data
}