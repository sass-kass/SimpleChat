package com.example.simplechat.ui.fragments.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.simplechat.databinding.ChatFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment: Fragment() {
    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    private val args: ChatFragmentArgs by navArgs()

    @Inject
    lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)

        viewModel.setEmail(args.companionEmail!!)

        binding.currentChatRecyclerView.adapter = adapter

        viewModel.getMessages()

        viewModel.messageList.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages)
        }

        binding.chatSendButton.setOnClickListener {
            viewModel.sendMessage(binding.chatTextField.text.toString())
        }

        return binding.root
    }
}