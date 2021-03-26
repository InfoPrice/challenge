package com.example.alodjinha.data.classes

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Banner {
    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("urlImagem")
    @Expose
    private var urlImage: String? = null

    @SerializedName("linkUrl")
    @Expose
    private var linkUrl: String? = null


    fun setId(Id: Int){ id = Id }

    fun getId() = id

    fun setUrlImage(UrlImage: String){ urlImage = UrlImage}

    fun getUrlImage() = urlImage

    fun setLinkUrl(LinkUrl: String){ linkUrl = LinkUrl }

    fun getLinkUrl() = linkUrl


}