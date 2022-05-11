package com.example.simplechat.ui.fragments.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechat.databinding.MyMessageItemBinding
import com.example.simplechat.databinding.OthersMessageItemBinding
import com.example.simplechat.firebase.Message
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ChatAdapter @Inject constructor(): ListAdapter<Message, RecyclerView.ViewHolder>(DiffCallbackMessages()) {

    companion object {
        private const val TYPE_TXT_SENT = 0
        private const val TYPE_TXT_RECEIVED = 1
    }

    class TxtSentViewHolder(val binding: MyMessageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.myMessageText.text = item.text
            binding.myMessageTime.text = item.createdAt.toString()
            binding.executePendingBindings()
        }
    }

    class TxtReceiveViewHolder(val binding: OthersMessageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.othersMessageText.text = item.text
            binding.othersMessageTime.text = item.createdAt.toString()
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            TYPE_TXT_SENT -> {
                val binding = MyMessageItemBinding.inflate(layoutInflater, parent, false)
                TxtSentViewHolder(binding)
            }
            else -> {
                val binding = OthersMessageItemBinding.inflate(layoutInflater, parent, false)
                TxtReceiveViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is TxtReceiveViewHolder -> holder.bind(getItem(position))
            is TxtSentViewHolder -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    class DiffCallbackMessages : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.createdAt == newItem.createdAt
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}