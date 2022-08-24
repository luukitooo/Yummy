package com.lukabaia.yummy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukabaia.yummy.databinding.ItemRandomBinding
import com.lukabaia.yummy.model.network.RandomRecipesInfo

class RandomAdapter() : ListAdapter<RandomRecipesInfo.RandomRecipe, RandomAdapter.RecipeViewHolder>(RandomRecipeItemCallback){

    var recipeClick: ((RandomRecipesInfo.RandomRecipe) -> Unit)? = null

    inner class RecipeViewHolder(private val binding: ItemRandomBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.apply {
                val recipe = getItem(adapterPosition)
                Glide.with(this.root).load(recipe.image).into(ivRecipe)
                tvRecipeTitle.text = recipe.title

                root.setOnClickListener {
                    recipeClick?.invoke(recipe)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(ItemRandomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind()
    }

    private object RandomRecipeItemCallback: DiffUtil.ItemCallback<RandomRecipesInfo.RandomRecipe>() {

        override fun areItemsTheSame(
            oldItem: RandomRecipesInfo.RandomRecipe,
            newItem: RandomRecipesInfo.RandomRecipe,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RandomRecipesInfo.RandomRecipe,
            newItem: RandomRecipesInfo.RandomRecipe,
        ): Boolean {
            return oldItem == newItem
        }
    }
}

