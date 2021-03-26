package com.example.lodjinha

import androidx.lifecycle.MutableLiveData
import com.example.alodjinha.data.Database
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.data.managers.LodjinhaManager
import org.junit.Assert
import org.junit.Test

class ProductsExibitionTest {
    val database =  Database()
    val lodjinhaManager =  LodjinhaManager(database)
    @Test
    fun databseManager_isCorrect() {
        val result = lodjinhaManager.getProducts().value

        var check = true
        if (result == null) {
            check = false
        }
            else
            check = true
        Assert.assertEquals(true, check)
    }


}