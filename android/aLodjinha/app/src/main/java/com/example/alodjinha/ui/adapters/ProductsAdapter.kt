package com.example.alodjinha.ui.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.alodjinha.R
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.ui.DescriptionActivity
import com.example.alodjinha.ui.DownloadImageWithURLTask
import com.example.alodjinha.ui.MainActivity
import kotlinx.android.synthetic.main.item_product.view.*


class ProductsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bindView(item: Product){
        with(view){
            val downloadImage = DownloadImageWithURLTask(iv_product_image)
            downloadImage.execute(item.getUrlImage())
            tv_name.text = item.getId().toString()
            tv_description.text = item.getDescription()


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
            val intent = Intent(it.context as AppCompatActivity, DescriptionActivity::class.java)
            intent.putExtra("Name", data[position].getName())
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