package com.fabricio.challenge.model;

import android.text.Html;
import android.text.Spanned;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @Expose
    @SerializedName("id")
    private Integer id;

    @Expose
    @SerializedName("categoria")
    private Category category;

    @Expose
    @SerializedName("descricao")
    private String description;

    @Expose
    @SerializedName("nome")
    private String name;

    @Expose
    @SerializedName("precoDe")
    private Double priceOriginal;

    @Expose
    @SerializedName("precoPor")
    private Double priceDiscount;

    @Expose
    @SerializedName("urlImagem")
    private String urlImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public Spanned getDescriptionFormatted() {
        return Html.fromHtml(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPriceOriginal() {
        return priceOriginal;
    }

    public void setPriceOriginal(Double priceOriginal) {
        this.priceOriginal = priceOriginal;
    }

    public Double getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(Double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
