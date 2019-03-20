package com.example.alodjinha.data.classes

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BannerList {

    @SerializedName("data")
    @Expose
    private var data: List<Banner>? = null

    fun setData(Data: List<Banner>){
        data = Data
    }

    fun getData() = data
}