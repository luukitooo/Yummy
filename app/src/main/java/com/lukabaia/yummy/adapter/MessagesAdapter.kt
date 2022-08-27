package com.lukabaia.yummy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lukabaia.yummy.databinding.ItemMessageAssistantBinding
import com.lukabaia.yummy.databinding.ItemMessageUserBinding
import com.lukabaia.yummy.model.Message

class MessagesAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(MessageItemCallback) {

    companion object {
        const val FROM_USER = 1
        const val FROM_ASSISTANT = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FROM_USER -> UserMessagesViewHolder(
                ItemMessageUserBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> AssistantMessagesViewHolder(
                ItemMessageAssistantBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is UserMessagesViewHolder -> holder.bind()
            is AssistantMessagesViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        return if (message.fromUser)
            FROM_USER
        else
            FROM_ASSISTANT
    }

    inner class AssistantMessagesViewHolder(private val binding: ItemMessageAssistantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvMessage.text = getItem(adapterPosition).message
        }

    }

    inner class UserMessagesViewHolder(private val binding: ItemMessageUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvMessage.text = getItem(adapterPosition).message
        }
    }

    private object MessageItemCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

}