package com.example.simplechat.ui.fragments.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.simplechat.databinding.UsersFragmentBinding
import com.example.simplechat.utils.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment: Fragment(), ItemClickListener {
    private var _binding: UsersFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UsersViewModel by viewModels()

    @Inject
    lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UsersFragmentBinding.inflate(inflater, container, false)

        viewModel.getUsers()

        binding.recyclerViewUsers.adapter = adapter

        UsersAdapter.itemClickListener = this

        viewModel.usersList.observe(viewLifecycleOwner) { users ->
            adapter.submitList(users)
        }

        return binding.root
    }

    override fun onItemClicked(v: View, position: Int) {
        val action = UsersFragmentDirections.actionUsersFragmentToChatFragment(viewModel.usersList.value?.get(position)?.email)
        findNavController().navigate(action)
    }
}