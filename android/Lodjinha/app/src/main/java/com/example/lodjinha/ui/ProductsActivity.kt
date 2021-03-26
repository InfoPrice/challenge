package com.example.lodjinha.ui

import android.os.Bundle
import android.app.Activity
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alodjinha.ui.adapters.ProductsAdapter
import com.example.alodjinha.viewmodels.LodjinhaViewModel
import com.example.lodjinha.R

import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.app_bar_about.*
import kotlinx.android.synthetic.main.app_bar_allproducts.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsActivity : AppCompatActivity() {
    private val lodjinhaViewModel: LodjinhaViewModel by viewModel()
    val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        setSupportActionBar(allproducts_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setTitle("")
        recycler_view_products.layoutManager = LinearLayoutManager(this)
        if (intent.getStringExtra("id").toInt() == 1)
        lodjinhaViewModel.loadAllProducts(20,40,intent.getStringExtra("id").toInt())
        else
            lodjinhaViewModel.loadAllProducts(0,20,intent.getStringExtra("id").toInt())
        lodjinhaViewModel.getAllProducts().observe(this, androidx.lifecycle.Observer {data ->
            data?.let {
                recycler_view_products.adapter = productsAdapter
                    productsAdapter.add(it)
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
