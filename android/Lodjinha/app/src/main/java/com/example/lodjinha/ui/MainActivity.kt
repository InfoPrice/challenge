package com.example.lodjinha.ui

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.alodjinha.ui.adapters.BannerAdapter
import com.example.alodjinha.ui.adapters.ProductsAdapter
import com.example.alodjinha.viewmodels.LodjinhaViewModel
import com.example.lodjinha.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.item_product.*
import kotlinx.android.synthetic.main.main_toolbar.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

        setContentView(R.layout.activity_main)
        setSupportActionBar(wld_toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, wld_toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        val fontPacifico = Typeface.createFromAsset(assets, "pacifico.ttf")
        dotsLayout = findViewById(R.id.dots_container)
        recycler_view.layoutManager = LinearLayoutManager(this)
        viewPager = findViewById(R.id.vp_banner) as ViewPager
        lodjinhaViewModel.loadProducts()

        lodjinhaViewModel.getProducts().observe(this, androidx.lifecycle.Observer {data ->
            data?.let {
                tv_name_menu.setTypeface(fontPacifico)
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
        lodjinhaViewModel.getBanners().observe(this, androidx.lifecycle.Observer {data ->
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_about -> {
                val intentAbout = Intent(this,AboutActivity::class.java)
                this.startActivity(intentAbout)
            }
            R.id.nav_exit -> {
            finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
                dots[i]!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dot))
            else
                dots[i]!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dot))

            var layoutParams: LinearLayout.LayoutParams
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            layoutParams.setMargins(4,0,4,0)
            dotsLayout!!.addView(dots[i],layoutParams)
            i++
        }
    }

    override fun onResume() {
        super.onResume()
        val fontPacifico = Typeface.createFromAsset(assets, "pacifico.ttf")
        val fontRobotoBold = Typeface.createFromAsset(assets, "Roboto-Bold.ttf")
        tv_category!!.setTypeface(fontRobotoBold)
        tv_sold!!.setTypeface(fontRobotoBold)
        tv_logo_menu!!.setTypeface(fontPacifico)
    }



}
