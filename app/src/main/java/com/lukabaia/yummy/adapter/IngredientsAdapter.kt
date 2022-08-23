package com.lukabaia.yummy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lukabaia.yummy.databinding.ItemIngredientBinding
import com.lukabaia.yummy.model.DetailedRecipesInfo

class IngredientsAdapter : ListAdapter<
        DetailedRecipesInfo.ExtendedIngredients,
        IngredientsAdapter.IngredientsViewHolder
        >(IngredientItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IngredientsViewHolder(
        ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind()
    }

    inner class IngredientsViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val ingredient = getItem(adapterPosition)
            binding.apply {
                tvName.text = ingredient.name
                tvAmount.text = ingredient.amount.toString().plus(" ${ingredient.unit}")
                tvConsistency.text = ingredient.consistency
                tvMeta.text = if (ingredient.meta.isEmpty())
                    "Not Found"
                else
                    ingredient.meta.toString()
            }
        }
    }

    private object IngredientItemCallback :
        DiffUtil.ItemCallback<DetailedRecipesInfo.ExtendedIngredients>() {
        override fun areItemsTheSame(
            oldItem: DetailedRecipesInfo.ExtendedIngredients,
            newItem: DetailedRecipesInfo.ExtendedIngredients
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DetailedRecipesInfo.ExtendedIngredients,
            newItem: DetailedRecipesInfo.ExtendedIngredients
        ): Boolean {
            return oldItem == newItem
        }
    }

}