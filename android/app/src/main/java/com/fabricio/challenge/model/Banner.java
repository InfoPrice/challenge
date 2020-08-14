package com.fabricio.challenge.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model of the Banner information fetch from the REST API
 * @author Fabricio Godoi
 */
public class Banner {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("linkUrl")
    private String linkUrl;

    @Expose
    @SerializedName("urlImagem")
    private String urlImage;

    transient private Bitmap image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
}
