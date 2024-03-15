package com.example.pique.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pique.data.Products
import com.example.pique.databinding.SpecialRvItemBinding
import com.squareup.picasso.Picasso


class SpecialProductsAdapter: RecyclerView.Adapter<SpecialProductsAdapter.SpecialProductsViewHolder>() {

    inner class SpecialProductsViewHolder(private val binding: SpecialRvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(products: Products){
            binding.apply {
//                Glide.with(itemView).load(products.images[0]).into(imgSpecialRvItem)
                Picasso.get().load(products.images[0]).into(imgSpecialRvItem)
                tvSpecialProductName.text = products.name
                tvSpecialProductPrice.text = "₹ ${products.price}"
            }
        }
    }


    private val diffCallback = object :DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialProductsViewHolder {
        return SpecialProductsViewHolder(
            SpecialRvItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: SpecialProductsViewHolder, position: Int) {
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