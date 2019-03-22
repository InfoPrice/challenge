package com.example.lodjinha

import androidx.lifecycle.MutableLiveData
import com.example.alodjinha.data.Database
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.data.managers.LodjinhaManager
import org.junit.Assert
import org.junit.Test

class ProductsExibitionTest {

    @Test
    fun databseManager_isCorrect() {


        val database =  Database()
        val lodjinhaManager =  LodjinhaManager(database)
        val result = lodjinhaManager.getProducts().value
        val check: Boolean
        if (result == null)
            check = false
            else
            check = true
        Assert.assertEquals(true, check)
    }

}