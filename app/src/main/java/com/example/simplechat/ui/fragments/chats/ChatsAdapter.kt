package com.example.simplechat.ui.fragments.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.simplechat.R
import com.example.simplechat.databinding.ChatsItemBinding
import com.example.simplechat.firebase.User
import com.example.simplechat.utils.ItemClickListener
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class ChatsAdapter @Inject constructor() : ListAdapter<User, ChatsAdapter.ViewHolder>(ChatsComparator()) {

    companion object {
        var itemClickListener: ItemClickListener? = null
    }

    class ViewHolder(val binding: ChatsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            if(item.image == "R.drawable.ic_baseline_broken_image_24")
                binding.chatUserIconImage.load(R.drawable.ic_baseline_broken_image_24)
            else
                binding.chatUserIconImage.load(item.image)
            binding.chatUserNick.text = item.nickname
            binding.userViewRoot.setOnClickListener { view ->
                ChatsAdapter.itemClickListener?.onItemClicked(view, bindingAdapterPosition)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ChatsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bind(item)
    }

    class ChatsComparator: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}