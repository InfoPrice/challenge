package com.fabricio.challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model of the Category information fetch from the REST API
 * @author Fabricio Godoi
 */
public class Category {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("descricao")
    private String description;

    @Expose
    @SerializedName("urlImagem")
    private String urlImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
