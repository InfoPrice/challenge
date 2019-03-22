package com.example.alodjinha.data.managers

import com.example.alodjinha.data.Database

class LodjinhaManager(val database: Database) {

    fun getProducts() = database.insertProducts()

    fun getBanners() = database.insertBanners()
}