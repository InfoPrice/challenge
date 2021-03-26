package com.example.alodjinha.data.classes

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

    class Category {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("descricao")
    @Expose
    private var description: String? = null


    @SerializedName("urlImagem")
    @Expose
    private var urlImage: String? = null

    fun setId(Id: Int){ id = Id }

    fun getId() = id

    fun setUrlImage(UrlImage: String){ urlImage = UrlImage}

    fun getUrlImage() = urlImage

    fun setDescription(Description: String){ description = Description }

    fun getDescription() = description
}