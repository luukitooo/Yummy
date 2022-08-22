package com.lukabaia.yummy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lukabaia.yummy.databinding.ItemCategoryBinding
import com.lukabaia.yummy.utils.RecipeTypes

class CategoryAdapter: ListAdapter<RecipeTypes, CategoryAdapter.CategoryViewHolder>(RecipeItemCallback) {

    var onItemClickListener: ((RecipeTypes) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind()
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                val type = getItem(adapterPosition)
                tvCategory.text = type.value
                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(type)
                }
            }
        }
    }

    private object RecipeItemCallback: DiffUtil.ItemCallback<RecipeTypes>(){
        override fun areItemsTheSame(oldItem: RecipeTypes, newItem: RecipeTypes): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(oldItem: RecipeTypes, newItem: RecipeTypes): Boolean {
            return oldItem.value == newItem.value
        }
    }

}