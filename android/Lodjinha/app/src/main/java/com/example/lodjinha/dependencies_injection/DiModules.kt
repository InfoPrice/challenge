package com.example.alodjinha.dependencies_injection

import com.example.alodjinha.data.Database
import com.example.alodjinha.data.managers.LodjinhaManager
import com.example.alodjinha.viewmodels.LodjinhaViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object DiModules {
    val appModule = module {

        single { Database() }

        factory { LodjinhaManager(get()) }

        viewModel { LodjinhaViewModel(get()) }
    }
}