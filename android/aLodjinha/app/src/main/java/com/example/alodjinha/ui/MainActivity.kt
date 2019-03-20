package com.example.alodjinha.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alodjinha.R
import com.example.alodjinha.ui.adapters.ProductsAdapter
import com.example.alodjinha.viewmodels.LodjinhaViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel



class MainActivity : AppCompatActivity() {

    private val lodjinhaViewModel: LodjinhaViewModel by viewModel()
    val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter()
    }
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.alodjinha.R.layout.activity_main)

    recycler_view.layoutManager = LinearLayoutManager(this)

        lodjinhaViewModel.loadProducts()
        lodjinhaViewModel.getProducts().observe(this, Observer {data ->
            data?.let {
                recycler_view.adapter = productsAdapter
                if (it.size <= 20)
                    productsAdapter.add(it)
                else{
                    recycler_view.addOnScrollListener(prOnScrollListener)
                    while (count < 20){
                        productsAdapter.add(it[count])
                        count++
                    }
                }

            }
        })
    }

    private val prOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)


            if (isLastItemDisplaying(recyclerView)){
                Log.d("ItemDisplaying", "LOAD MORE ITEMS")
                //TODO: load remaining products
            }

        }

    }

    private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean{
        if (recyclerView.adapter?.itemCount != 0){
            var lastVisiblePosition: Int
            lastVisiblePosition = 20

            if (lastVisiblePosition != RecyclerView.NO_POSITION && lastVisiblePosition == recyclerView.adapter!!.itemCount)
            return true
        }


        return false
    }


}
