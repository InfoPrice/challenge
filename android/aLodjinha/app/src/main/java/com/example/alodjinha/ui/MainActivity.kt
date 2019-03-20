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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.alodjinha.R.layout.activity_main)

    recycler_view.layoutManager = LinearLayoutManager(this)

        lodjinhaViewModel.loadProducts()
        lodjinhaViewModel.getProducts().observe(this, Observer {data ->
            data?.let {
                if (it.isEmpty())
                    Toast.makeText(this,"Empty List!!", Toast.LENGTH_LONG).show()
                else

                    recycler_view.adapter = productsAdapter
                    recycler_view.addOnScrollListener(prOnScrollListener)
                    productsAdapter.add(it)
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
                Log.d("LOAD MORE","LOAAAAD MOOORE")
            }

        }

    }

    private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean{
        if (recyclerView.adapter?.itemCount != 0){
            var lastVisiblePosition: Int
            lastVisiblePosition = 5

            if (lastVisiblePosition != RecyclerView.NO_POSITION && lastVisiblePosition == recyclerView.adapter!!.itemCount)
            return true
        }


        return false
    }


}
