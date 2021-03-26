package com.example.alodjinha.data.managers

import com.example.alodjinha.data.Database

class LodjinhaManager(val database: Database) {

    fun getProducts() = database.insertProducts()

    fun getBanners() = database.insertBanners()

    fun getCategory() = database.insertCategory()

    fun getAllProducts(offset: Int, limit: Int, categoriaId: Int) =
        database.insertAllProducts(offset,limit,categoriaId)
}