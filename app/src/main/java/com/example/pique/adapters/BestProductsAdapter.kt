package com.example.pique.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pique.data.Products
import com.example.pique.databinding.BestDealsRvItemBinding
import com.example.pique.databinding.ProductRvItemBinding
import com.example.pique.helper.getProductPrice
import com.squareup.picasso.Picasso

class BestProductsAdapter :RecyclerView.Adapter<BestProductsAdapter.BestProductsViewHolder>(){

    inner class BestProductsViewHolder(private val binding: ProductRvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(products: Products){
            binding.apply {
                Picasso.get().load(products.images[0]).into(imgProduct)
                products.offerPercentage?.let {
                    val priceAfterOffer = products.offerPercentage.getProductPrice(products.price)
                    tvNewPrice.text= "$ ${String.format("%.2f",priceAfterOffer)}"

                    tvPrice.paintFlags= tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                if (products.offerPercentage == null)
                    tvNewPrice.visibility = View.INVISIBLE
                tvPrice.text= "$ ${products.price}"
                tvName.text = products.name
            }
        }
    }

    private val diffCallback= object: DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductsViewHolder {
        return BestProductsViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: BestProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick:((Products) -> Unit)? = null
}