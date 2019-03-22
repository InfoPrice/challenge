package com.example.lodjinha.ui

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.lodjinha.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.app_bar_about.*
import kotlinx.android.synthetic.main.app_bar_main.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(about_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setTitle("")

        val fontRobotoMedium = Typeface.createFromAsset(this.assets, "Roboto-Medium.ttf")
        val fontRobotoLight = Typeface.createFromAsset(this.assets, "Roboto-Light.ttf")
        val fontPacifico = Typeface.createFromAsset(this.assets, "pacifico.ttf")
        tv_aboutview.setTypeface(fontPacifico)
        tv_aboutview_devname.setTypeface(fontRobotoMedium)
        tv_aboutview_date.setTypeface(fontRobotoLight)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
