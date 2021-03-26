package com.example.alodjinha.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.alodjinha.data.classes.Banner
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.ui.DownloadImageWithURLTask
import com.example.alodjinha.ui.DownloadImageWithURLTaskBitmap
import com.example.lodjinha.R
import kotlinx.android.synthetic.main.item_product.view.*

class BannerAdapter(private val context: Context, private val urlList: List<Banner>): PagerAdapter() {
    override fun getCount(): Int {
        return urlList.size
    }


    private var layoutInflater: LayoutInflater? = null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`

        }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.swipe_layout, null)
        val image = v.findViewById<View>(R.id.iv_banner ) as ImageView
        val downloadImage = DownloadImageWithURLTaskBitmap(image)
        downloadImage.execute(urlList[position].getUrlImage())
        val vp = container as ViewPager
        vp.addView(v,0)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
       val vp = container as ViewPager
       val v = `object` as View
        vp.removeView(v)
    }


}





