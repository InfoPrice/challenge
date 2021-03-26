package com.example.lodjinha.ui.adapters

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.alodjinha.data.classes.Category
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.ui.DownloadImageWithURLTask
import com.example.lodjinha.R
import com.example.lodjinha.ui.DetailActivity
import com.example.lodjinha.ui.ProductsActivity
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class CategoryViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bindView(item: Category){
        with(view){
            val fontRoboto = Typeface.createFromAsset(this.context.assets, "roboto.ttf")
            val fontRobotoMedium = Typeface.createFromAsset(this.context.assets, "Roboto-Medium.ttf")
            val fontRobotoBold = Typeface.createFromAsset(this.context.assets, "Roboto-Bold.ttf")
            val downloadImage = DownloadImageWithURLTask(iv_category)
            downloadImage.execute(item.getUrlImage())

            tv_cat.text = item.getDescription().toString()
            tv_cat.typeface = fontRoboto
        }
    }

}
class CategoryAdapter(val data: MutableList<Category> = mutableListOf()): RecyclerView.Adapter<CategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindView(data[position])
        holder.view.setOnClickListener() {
            val intentProduct = Intent(it.context as AppCompatActivity, ProductsActivity::class.java)
            intentProduct.putExtra("id", data[position].getId().toString())
            it.context.startActivity(intentProduct)
        }
    }

    fun add(item: List<Category>){
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    fun add(item: Category){
        data.add(item)
        notifyDataSetChanged()
    }

    fun remove (item: Category){
        data.remove(item)
        notifyDataSetChanged()
    }

}