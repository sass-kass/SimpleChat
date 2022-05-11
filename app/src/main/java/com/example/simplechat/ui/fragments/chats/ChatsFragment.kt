package com.example.simplechat.ui.fragments.chats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.simplechat.databinding.ChatsFragmentBinding
import com.example.simplechat.utils.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatsFragment: Fragment(), ItemClickListener {
    private var _binding: ChatsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatsViewModel by viewModels()

    @Inject
    lateinit var adapter: ChatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatsFragmentBinding.inflate(inflater, container, false)

        viewModel.getChats()

        binding.recyclerViewChats.adapter = adapter

        ChatsAdapter.itemClickListener = this

        viewModel.chatsList.observe(viewLifecycleOwner) { chats ->
            if (chats.isNullOrEmpty())
                binding.noChatsText.visibility = View.VISIBLE
            else
                adapter.submitList(chats)
        }

        return binding.root
    }

    override fun onItemClicked(v: View, position: Int) {
        val action = ChatsFragmentDirections.actionChatsFragmentToChatFragment2(viewModel.chatsList.value?.get(position)?.email)
        findNavController().navigate(action)
    }
}