package com.lukabaia.yummy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukabaia.yummy.databinding.ItemRecipeBinding
import com.lukabaia.yummy.model.network.SearchedRecipesInfo

class RecipeAdapter: ListAdapter<
        SearchedRecipesInfo.SearchedRecipe,
        RecipeAdapter.RecipeViewHolder
        >(SearchRecipeItemCallback) {

    var onItemClickListener: ((SearchedRecipesInfo.SearchedRecipe) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecipeViewHolder(
        ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind()
    }


    inner class RecipeViewHolder(private val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                val recipe = getItem(adapterPosition)
                Glide.with(this.root).load(recipe.image).into(ivRecipe)
                tvRecipeTitle.text = recipe.title
                root.setOnClickListener {
                    onItemClickListener?.invoke(recipe)
                }
            }
        }
    }

    private object SearchRecipeItemCallback: DiffUtil.ItemCallback<SearchedRecipesInfo.SearchedRecipe>() {
        override fun areItemsTheSame(
            oldItem: SearchedRecipesInfo.SearchedRecipe,
            newItem: SearchedRecipesInfo.SearchedRecipe
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchedRecipesInfo.SearchedRecipe,
            newItem: SearchedRecipesInfo.SearchedRecipe
        ): Boolean {
            return oldItem == newItem
        }
    }
}