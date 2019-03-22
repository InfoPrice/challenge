package com.example.lodjinha

import com.example.alodjinha.data.Database
import com.example.alodjinha.data.managers.LodjinhaManager
import org.junit.Assert
import org.junit.Test

class CategorysExibitionTest {

    val database =  Database()
    val lodjinhaManager =  LodjinhaManager(database)
    @Test
    fun databseManager_isCorrect() {
        val result = lodjinhaManager.getCategory().value

        var check = true
        if (result == null) {
            check = false
        }
        else
            check = true
        Assert.assertEquals(true, check)
    }
}