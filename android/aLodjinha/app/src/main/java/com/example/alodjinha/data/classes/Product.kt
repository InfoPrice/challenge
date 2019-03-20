package com.example.alodjinha.data.classes

import com.example.alodjinha.data.classes.Category
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("nome")
    @Expose
    private var name: String? = null

    @SerializedName("urlImagem")
    @Expose
    private var urlImage: String? = null

    @SerializedName("descricao")
    @Expose
    private var description: String? = null

    @SerializedName("precoDe")
    @Expose
    private var price: Double? = null

    @SerializedName("precoPor")
    @Expose
    private var priceFor: Double? = null

    @SerializedName("categoria")
    @Expose
    private var category: Category? = null


    fun setId(Id: Int){ id = Id }

    fun getId() = id

    fun setName(Name: String){ name = Name }

    fun getName() = name

    fun setUrlImage(UrlImage: String){ urlImage = UrlImage}

    fun getUrlImage() = urlImage

    fun setDescription(Description: String){ description = Description }

    fun getDescription() = description

    fun setPrice(Price: Double){ price = Price }

    fun getPrice() = price

    fun setPriceFor(PriceFor: Double){ priceFor = PriceFor }

    fun getPriceFor() = priceFor

    fun setCategoryId(CatId: Int){
        category?.setId(CatId)
    }
    fun getCategoryId() = category?.getId()

    fun setCategoryDescription(CatDesc: String){
        category?.setDescription(CatDesc)
    }
    fun getCategoryDescription() = category?.getDescription()

    fun setCategoryUrlImage(CatUrl: String){
        category?.setUrlImage(CatUrl)
    }
    fun getCategoryUrlImage() = category?.getUrlImage()



}