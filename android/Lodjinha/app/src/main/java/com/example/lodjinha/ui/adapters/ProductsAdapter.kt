package com.example.alodjinha.ui.adapters

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.ui.DownloadImageWithURLTask

import com.example.lodjinha.R
import com.example.lodjinha.ui.DetailActivity
import kotlinx.android.synthetic.main.item_product.view.*


class ProductsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bindView(item: Product){
        with(view){
            val fontRoboto = Typeface.createFromAsset(this.context.assets, "roboto.ttf")
            val fontRobotoMedium = Typeface.createFromAsset(this.context.assets, "Roboto-Medium.ttf")
            val fontRobotoBold = Typeface.createFromAsset(this.context.assets, "Roboto-Bold.ttf")
            val downloadImage = DownloadImageWithURLTask(iv_product_image)
            downloadImage.execute(item.getUrlImage())
            tv_name.text = item.getName().toString() + " com uma descrição grandona"
            tv_name.setTypeface(fontRobotoMedium)
            tv_priceBefore.text = "De: " + item.getPrice().toString()
            tv_priceBefore.setTypeface(fontRobotoMedium)
            tv_priceFor.text ="Por " +   item.getPriceFor().toString()
            tv_priceFor.setTypeface(fontRobotoBold)
            tv_priceBefore.setPaintFlags(tv_priceBefore.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


        }
    }

}
class ProductsAdapter(val data: MutableList<Product> = mutableListOf()): RecyclerView.Adapter<ProductsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bindView(data[position])
     holder.view.setOnClickListener() {
           val intent = Intent(it.context as AppCompatActivity,DetailActivity::class.java)
            intent.putExtra("Name", data[position].getName())
            intent.putExtra("priceBefore", data[position].getPrice().toString())
            intent.putExtra("priceFor", data[position].getPriceFor().toString())
            intent.putExtra("Description", data[position].getDescription())
         intent.putExtra("imageUrl", data[position].getUrlImage())
            it.context.startActivity(intent)
        }
    }

    fun add(item: List<Product>){
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    fun add(item: Product){
        data.add(item)
        notifyDataSetChanged()
    }

    fun remove (item: Product){
        data.remove(item)
        notifyDataSetChanged()
    }

}