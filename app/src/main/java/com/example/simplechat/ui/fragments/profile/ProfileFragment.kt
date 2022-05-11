package com.example.simplechat.ui.fragments.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.simplechat.R
import com.example.simplechat.databinding.ProfileFragmentBinding
import com.example.simplechat.firebase.User
import com.example.simplechat.ui.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)

        viewModel.getUser()

        viewModel.profileResult.observe(viewLifecycleOwner) {
            if (viewModel.profileResult.value?.image == "R.drawable.ic_baseline_broken_image_24")
                binding.profilePicture.load(R.drawable.ic_baseline_broken_image_24)
            else
                binding.profilePicture.load(viewModel.profileResult.value?.image)
            binding.profileEmail.text = viewModel.profileResult.value?.email
            binding.profileName.text = viewModel.profileResult.value?.nickname
        }

        binding.signOutButton.setOnClickListener {
            viewModel.signOut()
        }

        viewModel.requestResult.observe(viewLifecycleOwner) {
            val intent = Intent(this.context, MainActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}