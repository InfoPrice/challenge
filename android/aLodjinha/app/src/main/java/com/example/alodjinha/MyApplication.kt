package com.example.alodjinha

import android.app.Application
import com.example.alodjinha.dependencies_injection.DiModules
import org.koin.android.ext.android.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(DiModules.appModule))
    }
}