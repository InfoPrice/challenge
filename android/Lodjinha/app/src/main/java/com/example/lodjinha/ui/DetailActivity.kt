package com.example.lodjinha.ui

import android.graphics.Paint
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import com.example.alodjinha.ui.DownloadImageWithURLTask
import com.example.lodjinha.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_product.view.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar_collapse)
        val fontRobotoMedium = Typeface.createFromAsset(this.assets, "Roboto-Medium.ttf")
        val fontRobotoBold = Typeface.createFromAsset(this.assets, "Roboto-Bold.ttf")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setTitle("")
        tv_detail_product_name.text = intent.getStringExtra("Name") +" com uma descrição grandona"
        tv_detail_product_name.setTypeface(fontRobotoBold)
        tv_detail_pricebefore.text = "De: " + intent.getStringExtra("priceBefore")
        tv_detail_pricebefore.setTypeface(fontRobotoMedium)
        tv_detail_pricefor.text ="Por " + intent.getStringExtra("priceFor")
        tv_detail_pricebefore.setPaintFlags(tv_detail_pricebefore.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        tv_detail_title.setTypeface(fontRobotoBold)
        tv_detail_pricefor.setTypeface(fontRobotoBold)
        tv_detail_description.text = Html.fromHtml(intent.getStringExtra("Description"))
        tv_detail_description.setTypeface(fontRobotoMedium)
        val downloadImage = DownloadImageWithURLTask(iv_detail)
        downloadImage.execute(intent.getStringExtra("imageUrl"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}


