package com.example.alodjinha.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.ui.adapters.BannerAdapter
import com.example.alodjinha.ui.adapters.ProductsAdapter
import com.example.alodjinha.viewmodels.LodjinhaViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import java.util.logging.Handler
import android.R
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get


class MainActivity : AppCompatActivity() {

    private var dotsLayout: LinearLayout? = null
    private var customPosition: Int = 0
    private var timer: Timer? = null
    private var current_position: Int = 0
    internal lateinit var viewPager: ViewPager
    private val lodjinhaViewModel: LodjinhaViewModel by viewModel()
    val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter()
    }
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.alodjinha.R.layout.activity_main)
    dotsLayout = findViewById(com.example.alodjinha.R.id.dots_container)
    recycler_view.layoutManager = LinearLayoutManager(this)
        viewPager = findViewById(com.example.alodjinha.R.id.vp_banner) as ViewPager
        lodjinhaViewModel.loadProducts()
        lodjinhaViewModel.getProducts().observe(this, Observer {data ->
            data?.let {
                recycler_view.adapter = productsAdapter
                if (it.size < 20) {
                    productsAdapter.add(it)
                }
                else{
                    recycler_view.addOnScrollListener(prOnScrollListener)
                    while (count < 20){
                        productsAdapter.add(it[count])
                        count++
                    }
                }

            }

        })

        lodjinhaViewModel.loadBanners()
        lodjinhaViewModel.getBanners().observe(this, Observer {data ->
            data?.let {
                val adapter = BannerAdapter(this, it)
                viewPager.adapter = adapter

            }
        prepareDots(customPosition++)
        createSlideShow()
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }
                override fun onPageSelected(position: Int) {
                    if(customPosition>2)
                        customPosition = 0
                    prepareDots(customPosition++)
                }

            })
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

    fun createSlideShow() {
        val handler= android.os.Handler()
        val runnable = Runnable {
            if (current_position == 3)
                current_position = 0
            viewPager.setCurrentItem(current_position++, true)
        }

        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
               handler.post(runnable)
            }

        }, 250, 2500)
    }

    fun prepareDots(currentSlidePotion: Int){
        if (dotsLayout!!.childCount > 0)
            dotsLayout!!.removeAllViews()
        var i = 0
        val dots: Array<ImageView?> = arrayOfNulls(3)
        while (i<3){
        dots[i] = ImageView(this)
        if (i == currentSlidePotion)
            dots[i]!!.setImageDrawable(ContextCompat.getDrawable(this,com.example.alodjinha.R.drawable.active_dot))
        else
            dots[i]!!.setImageDrawable(ContextCompat.getDrawable(this,com.example.alodjinha.R.drawable.inactive_dot))

            var layoutParams: LinearLayout.LayoutParams
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            layoutParams.setMargins(4,0,4,0)
            dotsLayout!!.addView(dots[i],layoutParams)
            i++
        }
    }



}
