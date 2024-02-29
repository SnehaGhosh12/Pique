package com.example.pique.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pique.databinding.ViewpagerImageItemBinding
import com.squareup.picasso.Picasso

class ViewPager2images: RecyclerView.Adapter<ViewPager2images.ViewPager2imagesViewHolder>() {

    inner class ViewPager2imagesViewHolder(val binding: ViewpagerImageItemBinding): ViewHolder(binding.root){

        fun bind(imagePath: String){
            Picasso.get().load(imagePath).into(binding.imageProductDetails)
        }
    }

    private val diffCallback = object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPager2imagesViewHolder {
        return ViewPager2imagesViewHolder(
            ViewpagerImageItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPager2imagesViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}