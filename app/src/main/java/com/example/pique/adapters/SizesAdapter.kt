package com.example.pique.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pique.databinding.ColorRvItemBinding
import com.example.pique.databinding.SizeRvItemBinding
import com.example.pique.databinding.ViewpagerImageItemBinding
import com.squareup.picasso.Picasso

class SizesAdapter: RecyclerView.Adapter<SizesAdapter.SizeViewHolder>() {

    private var selectedPosition = -1

    inner class SizeViewHolder(private val binding: SizeRvItemBinding):ViewHolder(binding.root){

        fun bind(size: String,position: Int) {
            binding.tvSize.text = size
            if (position == selectedPosition) {
                binding.apply {
                    imageShadow.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    imageShadow.visibility = View.INVISIBLE
                }
            }

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizesAdapter.SizeViewHolder {
        return SizeViewHolder(
            SizeRvItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: SizesAdapter.SizeViewHolder, position: Int) {
        val size = differ.currentList[position]
        holder.bind(size,position)

        holder.itemView.setOnClickListener {
            if(selectedPosition >= 0)
                notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
            onItemClick?.invoke(size)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onItemClick:((String)-> Unit)? = null
}