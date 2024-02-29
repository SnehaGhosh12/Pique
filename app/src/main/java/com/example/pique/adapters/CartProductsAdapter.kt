package com.example.pique.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pique.data.CartProduct
import com.example.pique.data.Products
import com.example.pique.databinding.CartProductItemBinding
import com.example.pique.databinding.SpecialRvItemBinding
import com.example.pique.helper.getProductPrice
import com.squareup.picasso.Picasso

class CartProductsAdapter: RecyclerView.Adapter<CartProductsAdapter.CartProductsViewHolder>() {

    inner class CartProductsViewHolder(val binding: CartProductItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(cartProducts: CartProduct){
            binding.apply {
                Picasso.get().load(cartProducts.products.images[0]).into(imageCartProduct)
                tvProductCartName.text = cartProducts.products.name
                tvProductCartQuantity.text = cartProducts.quantity.toString()

                val priceAfterPercentage = cartProducts.products.offerPercentage.getProductPrice(cartProducts.products.price)
                tvProductCartPrice.text = "$ ${String.format("%.2f",priceAfterPercentage)}"

                imageCartProductColor.setImageDrawable(ColorDrawable(cartProducts.selectedColor?: Color.TRANSPARENT))
                tvCartProductSize.text = cartProducts.selectedSize?:"".also { imageCartProductSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT)) }
            }
        }
    }


    private val diffCallback = object : DiffUtil.ItemCallback<CartProduct>(){
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.products.id == newItem.products.id
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductsViewHolder {
        return CartProductsViewHolder(
            CartProductItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: CartProductsViewHolder, position: Int) {
        val cartProduct = differ.currentList[position]
        holder.bind(cartProduct)

        holder.itemView.setOnClickListener {
            onProductClick?.invoke(cartProduct)
        }

        holder.binding.btnPlus.setOnClickListener {
            onPlusClick?.invoke(cartProduct)
        }
        holder.binding.btnMinus.setOnClickListener {
            onMinusClick?.invoke(cartProduct)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onProductClick:((CartProduct) -> Unit)? = null
    var onPlusClick:((CartProduct) -> Unit)? = null
    var onMinusClick:((CartProduct) -> Unit)? = null

}