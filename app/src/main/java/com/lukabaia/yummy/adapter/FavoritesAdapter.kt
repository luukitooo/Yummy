package com.lukabaia.yummy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukabaia.yummy.databinding.ItemFavoriteBinding
import com.lukabaia.yummy.model.FavoriteRecipe

class FavoritesAdapter: ListAdapter<FavoriteRecipe, FavoritesAdapter.FavoritesViewHolder>(FavoriteItemCallBack) {

    var onItemClickListener: ((FavoriteRecipe) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoritesViewHolder(
        ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind()
    }

    inner class FavoritesViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val favorite = getItem(adapterPosition)
            binding.apply {
                Glide.with(this.root).load(favorite.image).into(ivRecipe)
                tvTitle.text = favorite.title
                root.setOnClickListener {
                    onItemClickListener?.invoke(favorite)
                }
                itemView.tag = favorite.title
            }
        }
    }

    private object FavoriteItemCallBack: DiffUtil.ItemCallback<FavoriteRecipe>() {
        override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
            return oldItem == newItem
        }
    }

}