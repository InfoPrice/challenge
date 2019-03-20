package com.example.alodjinha.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.alodjinha.R
import com.example.alodjinha.data.classes.Product
import com.example.alodjinha.ui.DownloadImageWithURLTask
import kotlinx.android.synthetic.main.item_product.view.*


class ProductsViewHolder(val view: View): RecyclerView.ViewHolder(view){
    fun bindView(item: Product){
        with(view){
            var downloadImage = DownloadImageWithURLTask(iv_product_image)
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
     //   holder.view.setOnClickListener() {
          //  val bundle = Bundle()
            //bundle.putString("imdbid_recived", data[position].getImdbid())
            //val movieDetails =  MovieDetails()
           // movieDetails.arguments = bundle
           // val activity = it.context as AppCompatActivity
          //  val transaction : FragmentTransaction
           // transaction = activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_content, movieDetails)
           // transaction.addToBackStack(null).commit()


        //}



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