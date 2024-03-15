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
import com.example.pique.databinding.SpecialRvItemBinding
import com.squareup.picasso.Picasso

class BestDealsAdapter:RecyclerView.Adapter<BestDealsAdapter.BestDealsViewHolder>() {

    inner class BestDealsViewHolder(private val binding: BestDealsRvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(products: Products){
            binding.apply {
                Picasso.get().load(products.images[0]).into(imgBestDeal)
                products.offerPercentage?.let {
                    val remainingPricePercentage = 1f - it
                    val priceAfterOffer =(remainingPricePercentage * products.price)

                    tvNewPrice.text= "₹ ${String.format("%.2f",priceAfterOffer)}"
                    tvOldPrice.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
                }
                if(products.offerPercentage==null){
                    tvNewPrice.visibility = View.INVISIBLE
                }


                tvOldPrice.text= "₹ ${products.price}"
                tvDealProductName.text = products.name
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsViewHolder {
        return BestDealsViewHolder(
            BestDealsRvItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: BestDealsViewHolder, position: Int) {
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